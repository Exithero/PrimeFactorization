import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;


public class Client {

	public static void main(String args[]){
		String word="175891579187581657617";
		Kattio io=new Kattio(System.in,System.out);
		BigInteger number = new BigInteger(word);
		Factorer faack=new Factorer(io,System.currentTimeMillis()+20000);
		
	
		long nrOfRuns=10;
		ArrayList<BigInteger> list=new ArrayList<BigInteger>();
		while((word=io.getWord())!=null){
//			System.out.println(word);
			list.add(new BigInteger(word));
//			faack.factorizeTrial(new BigInteger(word),nrOfRuns);
//			faack.factorizePollard(new BigInteger(word),nrOfRuns);
			faack.factorizeFermat(new BigInteger(word), nrOfRuns);
			faack.printRes();
			nrOfRuns--;
			
		}
//		for(BigInteger start=new BigInteger("1758915");start.compareTo(number)<=0;start=start.add(BigInteger.ONE)){
//		
//			faack.factorizeTrial(start,nrOfRuns);
//			ArrayList<BigInteger> trialResult=faack.getResult();
//			faack.factorizePollard(start,nrOfRuns);
//			ArrayList<BigInteger> pollardResult=faack.getResult();
//			
//				
//			if(!testResults(pollardResult,trialResult)){
//				System.out.println("wrong answer: "+start);
//				faack.printRes();
//				System.out.println();
//				faack.factorizeTrial(start,nrOfRuns);
//				faack.printRes();
//				System.out.println();
//			}else{
//				System.out.println("correct"+ start);
//				
//			}
//			System.out.println(faack.testisqrt(start));
//			if(!faack.testisqrt(start)){
//				System.out.println(start+" power of"+start.pow(2));
//			}
//			
//		}
//		System.out.println("STARTING TRIALDIVISION");
//		faack.factorizeTrial(new BigInteger(word));
		/*ArrayList<BigInteger> trialResult=faack.getResult();
		for(BigInteger b: trialResult){
			System.out.println(b);
		}*/
//		System.out.println("STARTING PollardRho");
	/*	faack.factorizePollard(new BigInteger(word));
		ArrayList<BigInteger> pollardResult=faack.getResult();
		System.out.println();
		for(BigInteger b: pollardResult){
			System.out.println(b);
			System.out.println(b);
		}*/
//		System.out.println(faack.multiplicationTest(new BigInteger(word)));
	
//		System.out.println(testResults(trialResult,pollardResult));
		
	}
	public static boolean testResults(ArrayList<BigInteger> l1,ArrayList<BigInteger> l2){
		if(l1!=null&&l2!=null){
		Collections.sort(l1);
		Collections.sort(l2);
		return l1.equals(l2);
		}
		return false;
		
	}
	
	
	
	
}
