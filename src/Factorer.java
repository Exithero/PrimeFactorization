import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;


public class Factorer {
	private BigInteger number;
	public final static BigInteger two = new BigInteger("2");
	public final static BigInteger three = new BigInteger("3");
	long start;
	Kattio io;
	long timePerRun;
	long endTime;
	
	ArrayList<BigInteger> res;
	
	int[] initprimes;
//	= new int[]{2,3,5,7,11,13,17,19,23,29
//								,31,37,41,43,47,53,59,61,67,71 
//								,73,79,83,89,97,101,103,107,109,113 
//								,127,131,137,139,149,151,157,163,167,173 
//								,179,181,191,193,197,199,211,223,227,229 
//								,233,239,241,251,257,263,269,271,277,281 
//								,283,293,307,311,313,317,331,337,347,349 
//								,353,359,367,373,379,383,389,397,401,409 
//								,419,421,431,433,439,443,449,457,461,463 
//								,467,479,487,491,499,503,509,521,523,541 
//								,547,557,563,569,571,577,587,593,599,601 
//								,607,613,617,619,631,641,643,647,653,659 
//								,661,673,677,683,691,701,709,719,727,733 
//								,739,743,751,757,761,769,773,787,797,809 
//								,811,821,823,827,829,839,853,857,859,863 
//								,877,881,883,887,907,911,919,929,937,941 
//								,947,953,967,971,977,983,991,997};
	
	public Factorer(Kattio io,long endTime){
		initprimes=primeSieve(1000000);
		this.io=io;
		this.endTime=endTime;
	}
	public void factorizePollard(BigInteger N,long nrOfRuns){
		initialize(nrOfRuns);
		pollardRho(N,BigInteger.ONE);
		
		
	}
	public void factorizeTrial(BigInteger N,long nrOfRuns){
		
		initialize(nrOfRuns);

		trialDivision(N,three);
		
	}
public void factorizeTrial2(BigInteger N,long nrOfRuns){
		
		initialize(nrOfRuns);

		trialDivision2(N,three);
		
	}
	
	
	private void initialize(long nrOfRuns){
		res=new ArrayList<BigInteger>();
//		updateTimeInterval(nrOfRuns);
		start= System.currentTimeMillis()+(endTime-System.currentTimeMillis())/nrOfRuns;;
		
		
	}
	/*private void updateTimeInterval(long nrOfRuns){
		timePerRun=(endTime-System.currentTimeMillis())/nrOfRuns;
	}*/
	public ArrayList<BigInteger> getResult(){
		return res;
	}
	public void printRes(){

//		System.out.println("WEEE");
		
		if(res==null){
			io.println("fail");
//			System.out.println("fail");
		}else{
			Collections.sort(res);
			for(BigInteger b: res){
				io.println(b);
//				System.out.println(b);
			}
		}
		io.println();
		io.flush();

	}

	private void pollardRho(BigInteger N,BigInteger c){
		System.out.println(N);
//		System.out.println("doing "+N.toString());
		//it's a prime factor itself
//		System.out.println(N);
		if(N.equals(BigInteger.ONE)){
			return;
		}
		if(N.isProbablePrime(200)){
			res.add(N);
			return;
		}
		if(System.currentTimeMillis()>start){
			res=null;
			return;
		}
		

		BigInteger x=two;
		BigInteger y=two;
		BigInteger d=BigInteger.ONE;

		while(d.equals(BigInteger.ONE)){
			if(System.currentTimeMillis()>start){
				res=null;
				return;
			}		
			x= f(x,c,N);
			y= f(f(y,c,N),c,N);
			d=x.subtract(y).abs().gcd(N);
		}
		if(d.equals(N)){
//			System.out.println("OMG WTF "+N);
			pollardRho(N.divide(d),c.add(BigInteger.ONE));
//			trialDivision(N,three);
//			something is wrong
			return;
		} else {
//			trialDivision(d,three);
			pollardRho(d,BigInteger.ONE);
//			System.out.println("WEEEE");
//			System.out.println("YAAY "+d.toString());
			pollardRho(N.divide(d),BigInteger.ONE);
			return;
		}
	}
	
	/**
	 * pseudo random thingy
	 * @return
	 */
	public BigInteger f(BigInteger x, BigInteger c,BigInteger N){
//		System.out.println("fx "+x.shiftLeft(1).add(c).remainder(N));
		return x.pow(2).add(c).mod(N);
		
	}
	
	public boolean multiplicationTest(BigInteger facit){
		BigInteger x=BigInteger.ONE;
		for(BigInteger b:res){
			x=x.multiply(b);
		}
		System.out.println("multiplication test "+x+" Facit "+facit);
		return x.equals(facit);
	}
	
	
	private void trialDivision(BigInteger N,BigInteger startFactor){
		
		
		if(N.isProbablePrime(100)){
			res.add(N);
			return;
		}
		if(System.currentTimeMillis()>start){
			res=null;
			return;
		}
		
		BigInteger limit = N.shiftRight((N.bitCount()/2)-1);
		if(!N.testBit(0)){
			res.add(two);
			trialDivision(N.divide(two),startFactor);
			return;
		}
		for(BigInteger i=startFactor; i.compareTo(limit)<=0; i=i.add(two)){
			if(System.currentTimeMillis()>start){
				res=null;
				return;
			}
			if(N.mod(i).equals(BigInteger.ZERO)){
				res.add(i);
				trialDivision(N.divide(i),i);
				return;
				
			}
			
		}
		
		
	}
	
	private void trialDivision2(BigInteger N,BigInteger startFactor){
		
		if(N.isProbablePrime(100)){
			res.add(N);
			return;
		}
		if(System.currentTimeMillis()>start){
			res=null;
			return;
		}
		
		BigInteger limit = N.shiftRight((N.bitCount()/2)-1);
		if(!N.testBit(0)){
			res.add(two);
			trialDivision2(N.divide(two),startFactor);
			return;
		}

		for(int index=0; true; index++){
			BigInteger i;
			if(index>=initprimes.length){


				i	=new BigInteger(new Integer(initprimes[initprimes.length-1]+(index-initprimes.length)*2).toString());
			}else{
				i=new BigInteger(new Integer(initprimes[index]).toString());
			}
			//					i.compareTo(limit)<=0
			if(System.currentTimeMillis()>start){
				res=null;
				return;
			}
			if(N.mod(i).equals(BigInteger.ZERO)){
				res.add(i);
				trialDivision2(N.divide(i),i);
				return;
				
			}
			
		}
		
		
	}
	
	public static int[] primeSieve(int to){
		boolean bo[]=new boolean[to+1];
		bo[0]=true;
		bo[1]=true;
		for(int i=2;i<bo.length;i++){
			if(!bo[i]){
				int prime=i;
				for(int j=prime*2;j<bo.length;j+=prime){
					bo[j]=true;
				}
			}
		}
		
		int size=0;
		for(int i=0;i<bo.length;i++){
			if(!bo[i]){
				size++;
			}
		}
		
		int[] res=new int[size];
		
		int index=0;
		
		for(int i=0;i<bo.length;i++){
			if(!bo[i]){
				res[index]=i;
				index++;
			}
		}
		
		return res;
	}
}
