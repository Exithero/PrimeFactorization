import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
//		System.out.println("primes: "+initprimes.length);
		this.io=io;
		this.endTime=endTime;
	}
	public void factorizePollard(BigInteger N,long nrOfRuns){
		initialize(nrOfRuns);
		pollardRho(N,BigInteger.ONE);
		
		
	}
	public void factorizeFermat(BigInteger N,long nrOfRuns){
		initialize(nrOfRuns);
		fermat2(N);
		
		
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
//		System.out.println(N);
//		System.out.println("doing "+N.toString());
		//it's a prime factor itself
//		System.out.println(N);
		
		if(N.isProbablePrime(100)){
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
//			pollardRho(N.divide(d),c.add(BigInteger.ONE));
			trialDivision3(N,2000,-1);
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
		if(res==null){ return false; }
		BigInteger x=BigInteger.ONE;
		for(BigInteger b:res){
			x=x.multiply(b);
		}
		System.out.println("multiplication test "+x+" Facit "+facit);
		return x.equals(facit);
	}
	
	public boolean primeTest(){
		if(res==null){ return false; }
		for(BigInteger b:res){
			if(!b.isProbablePrime(400)){
				return false;
			}
		}
		return true;
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
	
	private BigInteger almostSqrt(BigInteger N){
		return N.shiftRight((N.bitCount()/2)-1);
	}
	
	private void trialDivision2(BigInteger N,BigInteger startFactor){
		final int certainty=100;
		
		if(N.isProbablePrime(certainty)){
			res.add(N);
			return;
		}
		
		if(System.currentTimeMillis()>start){
			res=null;
			return;
		}
		
		//if it is a prime returned earlier, if not it will find it before limit
		//BigInteger limit = N.shiftRight((N.bitCount()/2)-1);
		while(!N.testBit(0)){
			res.add(two);
			//trialDivision2(N.divide(two),startFactor);
			N=N.divide(two);
			if(N.isProbablePrime(certainty)){
				res.add(N);
				return;
			}
		}
		
		for(int index=0; true; index++){
			BigInteger i;
			if(index>=initprimes.length){
				i=new BigInteger(new Integer(initprimes[initprimes.length-1]+(index-initprimes.length)*2).toString());
			}else{
				i=new BigInteger(new Integer(initprimes[index]).toString());
			}
			
			
			if(System.currentTimeMillis()>start){
				res=null;
				return;
			}
			if(N.mod(i).equals(BigInteger.ZERO)){
				res.add(i);
				N=N.divide(i);
				if(N.isProbablePrime(certainty)){
					res.add(N);
					return;
				}
				return;
				
			}
			
		}
		
		
	}
	
	
	public void trialDivision4(BigInteger N){
		N=trialDivision3(N, 0,this.initprimes.length-1);
		if(N==null || N.equals(BigInteger.ONE)){
			return;
		}
		
		long timeLimit=start;
		
		
		BigInteger factor=new BigInteger(new Integer(initprimes[initprimes.length-1]).toString());
		for(;true ; factor=factor.add(two)){
			//check time
			if(System.currentTimeMillis()>timeLimit){
				res=null;
				return;
			}
			//try division
			if(N.mod(factor).equals(BigInteger.ZERO)){
				res.add(factor);
				N=N.divide(factor);
				if(N.isProbablePrime(100)){
					res.add(N);
					return;
				}
			}
			
		}
	}
	
	/**
	 * uses trial division first and then uses pollard rho.
	 * @param N
	 * @param nrOfRuns
	 */
	public void factorizeTrial3(BigInteger N,long nrOfRuns){
		
		initialize(nrOfRuns);
//		startTrialDivision3(N,-1);
//		N=trialDivision3(N, 0, 2000);
//		if(N!=null && !N.equals(BigInteger.ONE)){
//			res.add(N);
//		}
		
		
		N=trialDivision3(N, 0, 2000);
		if(N!=null){
			if(!N.equals(BigInteger.ONE)){
				pollardRho(N,BigInteger.ONE);
			}
		}
//		if(N==null){
//			System.out.println("timeout");
//		} else {
//			System.out.println("Factor left: "+N);
//		}
//		return N;
		
	}
	
//	/**
//	 * calls trialDivision3
//	 * @param N
//	 * @param toPrimeIndex
//	 * @return
//	 */
//	public BigInteger startTrialDivision3(BigInteger N, int toPrimeIndex){
//		return trialDivision3(N, 0, toPrimeIndex);
//	}
	
	/**
	 * return 1 if factored all.
	 * returns the number that is left if didn't factor all.
	 * returns null if out of time
	 * 
	 * if toPrimeIndex is -1 then it will try to find all prime factors even those that
	 * aren't in the list of primes
	 * @param N
	 * @param toPrimeIndex
	 * @return
	 */
	public BigInteger trialDivision3(BigInteger N, int fromPrimeIndex, int toPrimeIndex){
		//change to this better name for the time limit
		long timeLimit=start;
		
		final int certainty=100;
		
		//return if it's a prime number
		if(N.isProbablePrime(certainty)){
			res.add(N);
			return BigInteger.ONE;
		}
		//check time
		if(System.currentTimeMillis()>timeLimit){
			res=null;
			return null;
		}
		//divide by two
		while(!N.testBit(0)){
			res.add(two);
			//trialDivision2(N.divide(two),startFactor);
			N=N.divide(two);
			if(N.isProbablePrime(certainty)){
				res.add(N);
				return BigInteger.ONE;
			}
		}
		
		//BigInteger limit = N.shiftRight((N.bitCount()/2)-1);
		//Unnecessary it's not a prime so will find factor before limit
		//BigInteger limit = almostSqrt(N);
		
		//have already tested 2
		if(fromPrimeIndex==0){
			fromPrimeIndex=1;
		}
		
		//no limit
		boolean full=false;
		if(toPrimeIndex==-1){
			full=true;
			toPrimeIndex=this.initprimes.length-1;
		}
		
		
		
		for(int index=fromPrimeIndex; index<=toPrimeIndex;){
			//get prime factor
			BigInteger factor=new BigInteger(new Integer(initprimes[index]).toString());//slow?
			//check if already finished
//			if(factor.compareTo(limit)>0){
//				break;
//			}
			
			//check time
			if(System.currentTimeMillis()>timeLimit){
				res=null;
				return null;
			}
			//try division
			if(N.mod(factor).equals(BigInteger.ZERO)){
				res.add(factor);
				//return trialDivision3(N.divide(factor),index,toPrimeIndex);
				N=N.divide(factor);
				if(N.isProbablePrime(certainty)){
					res.add(N);
					return BigInteger.ONE;
				}
			} else{
				//go to next if did no division
				index++;
			}
			
		}
		
		//if there is no upper limit eg toPrimeIndex was -1
		if(full){
//			System.out.println("Testing above upper bound: ");
			//get first prime factor
			BigInteger factor=new BigInteger(new Integer(initprimes[toPrimeIndex]).toString()).add(two);
			for(;;factor.add(two)){
				//check time
				if(System.currentTimeMillis()>timeLimit){
					res=null;
					return null;
				}
				//try division
				if(N.mod(factor).equals(BigInteger.ZERO)){
					res.add(factor);
					//return trialDivision3(N.divide(factor),index,toPrimeIndex);
					N=N.divide(factor);
					if(N.isProbablePrime(certainty)){
						res.add(N);
						return BigInteger.ONE;
					}
				} else{
					//go to next if did no division
					factor=factor.add(two);
				}
			}
			
			//should never exit the loop except if N is now a prime
		}
		
		
		
		return N;
	}
	
	
	
//	
//	FermatFactor(N): // N should be odd
//	    a ← ceil(sqrt(N))
//	    b2 ← a*a - N
//	    while b2 isn't a square:
//	        a ← a + 1    // equivalently: b2 ← b2 + 2*a + 1
//	        b2 ← a*a - N //               a ← a + 1
//	    endwhile
//	    return a - sqrt(b2) // or a + sqrt(b2)
//	
	
//	function fermatFactor(N)
//	for x from ceil(sqrt(N)) to N
//	ySquared := x * x - N
//	if isSquare(ySquared) then
//	y := sqrt(ySquared)
//	s := (x - y)
//	t := (x + y)
//	if s <> 1 and s <> N then
//	return s, t
//	end if
//	end if
//	end for
//	end function
	public void fermat2(BigInteger n){
		if(n.isProbablePrime(200)){
			System.out.println("found prime "+n);
			res.add(n);
			return;
		}
		System.out.println(sqrt(three));
		if(n.mod(two).equals(BigInteger.ZERO)){
			res.add(two);
			fermat2(n.divide(two));
			return;
		}
		BigInteger start=sqrt(n);
	
		if(!isSquare(n)){
			start=start.add(BigInteger.ONE);
		}
		for(BigInteger x=start;x.compareTo(n)<0;x=x.add(BigInteger.ONE)){
			BigInteger ySquared=x.pow(2).subtract(n);
			if(isSquare(ySquared)){
				BigInteger y=sqrt(ySquared);
				BigInteger s=x.subtract(y);
				BigInteger t=x.add(y);
				if(!s.equals(BigInteger.ONE)&&!s.equals(n)){
					fermat2(s);
					fermat2(t);
				}
			}
			
		}
	}
	
	public void fermat(BigInteger n){
		if(n.equals(BigInteger.ONE)){
			return;
		}
		if(n.isProbablePrime(200)){
			res.add(n);
			return;
		}

		if(n.mod(two).equals(BigInteger.ZERO)){
			res.add(two);
			fermat(n.divide(two));

			return;
		}

		BigInteger a=sqrt(n);
		if(!isSquare(n)){
			a=a.add(BigInteger.ONE);
		}

		BigInteger b2=a.pow(2).subtract(n);

		while(!isSquare(b2)){
		
			a=a.add(BigInteger.ONE);
			b2=a.pow(2).subtract(n);
//			System.out.println(b2);
	
			
//			System.out.sprintln(b2+ " a "+ a);
		}
		System.out.println("factor: "+a.add(sqrt(b2)));
		System.out.println("factor: sub "+a.subtract(sqrt(b2)));
		fermat(a.add(sqrt(b2)));
		fermat(a.subtract((sqrt(b2))));
//		return a.add(isqrt(b2));
	}
//	static uint isqrt(uint x)
//	{
//	    int b=15; // this is the next bit we try 
//	    uint r=0; // r will contain the result
//	    uint r2=0; // here we maintain r squared
//	    while(b>=0) 
//	    {
//	        uint sr2=r2;
//	        uint sr=r;
//	                    // compute (r+(1<<b))**2, we have r**2 already.
//	        r2+=(uint)((r<<(1+b))+(1<<(b+b)));      
//	        r+=(uint)(1<<b);
//	        if (r2>x) 
//	        {
//	            r=sr;
//	            r2=sr2;
//	        }
//	        b--;
//	    }
//	    return r;
//	}
	private boolean isSquare(BigInteger a){
		return sqrt(a).pow(2).equals(a);
	}
	
	public BigInteger oldisqrt(BigInteger x){
		int b=x.bitCount();
		BigInteger r=BigInteger.ZERO;
		BigInteger r2=BigInteger.ZERO;
		 while(b>=0) 
		    {
		        BigInteger sr2=r2;
		        BigInteger sr=r;
		                    // compute (r+(1<<b))**2, we have r**2 already.
		        r2=r2.add(r.shiftLeft(1+b).add(BigInteger.ONE.shiftLeft(b+b)));
		        r=r.add(BigInteger.ONE.shiftLeft(b));
		       
		        if (r2.compareTo(x)>0) 
		        {
		            r=sr;
		            r2=sr2;
		        }
		        b--;
		    }
		 return r;
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
	
	public boolean testisqrt(BigInteger i){
		return sqrt(i.pow(2)).equals(i);
	}
	
	public static BigInteger sqrt(BigInteger n)
	{
	 /*   if (n.signum() >= 0)
	    {
	        final int bitLength = n.bitLength();
	        BigInteger root = BigInteger.ONE.shiftLeft(bitLength / 2);

	        while (!isSqrt(n, root))
	        {
	            root = root.add(n.divide(root)).divide(two);
	        }
	        return root;
	    }
	    else
	    {
	        throw new ArithmeticException("square root of negative number");
	    }*/
		return bigRoot(n);//isqrt(n);//SquareRoot.squareRoot(n, true);
	}
	
	
	public static BigInteger isqrt(BigInteger n){
//		System.out.println(n);
		if(n.compareTo(three)<=0){
			return BigInteger.ONE;
		}
//		System.out.println(n);
		BigInteger x=n;
		BigInteger x2=n.add(BigInteger.ONE);
		
		while(!(x.subtract(x2).abs().compareTo(BigInteger.ONE)<1)){
			x=x2;
//			if(x<=x2){
//				break;
//			}
			x2=x.add(n.divide(x)).divide(two);
//			System.out.println(x+" "+x2);
		}
//		System.out.println(x2);
		return x2;
	}
	
	private static BigInteger bigRoot(BigInteger n) {
		  BigInteger a = BigInteger.ONE;
		  BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		  while(b.compareTo(a) >= 0) {
		    BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
		    if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
		    else a = mid.add(BigInteger.ONE);
		  }
		  return a.subtract(BigInteger.ONE);
		}


//	private static boolean isSqrt(BigInteger n, BigInteger root)
//	{
//	    final BigInteger lowerBound = root.pow(2);
//	    final BigInteger upperBound = root.add(BigInteger.ONE).pow(2);
//	    return lowerBound.compareTo(n) <= 0
//	        && n.compareTo(upperBound) < 0;
//	}
	
	
	public static BigInteger binSqrt(final BigInteger n){
		BigInteger upper=n.shiftRight(n.bitCount()-1);
		BigInteger lower=upper.shiftRight(1);
		BigInteger tmp=lower;
		
		//linear search
//		while(true){
//			tmp=lower.pow(2);
//			if(tmp.compareTo(n)>0){
//				return lower.subtract(BigInteger.ONE);
//			}
//			lower.add(BigInteger.ONE);
//		}
		
		//binary search
		BigInteger middle=null;
		while(lower.compareTo(upper)<0){
			middle=lower.add(upper.subtract(lower).divide(two));
			tmp=middle.pow(2);
			if(tmp.compareTo(n)==0){
				return middle;
			}
			if(tmp.compareTo(n)>0){
				upper=middle.subtract(BigInteger.ONE);
			} else {
				lower=middle.add(BigInteger.ONE);
			}
			System.out.println("woo"+n+" upper "+upper+" lower "+lower);
		}
		if(middle==null){
			System.out.println("last");
			System.out.println("woo"+n+" upper "+upper+" lower "+lower);
		}
		tmp=middle.pow(2);
		if(tmp.compareTo(n)>0){
			return middle.subtract(BigInteger.ONE);
		} else {
			return middle;
		}
		
	}
	
	

}
