
public class Client {

	public static void main(String args[]){
		String word="";
		Factorer faack;
		Kattio io=new Kattio(System.in,System.out);
		while((word=io.getWord())!=""){
			faack=new Factorer(word);
			
		}
		
		
	}
}
