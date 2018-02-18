import java.util.*; 
import java.io.*; 

public class HuffManCompressor {
	public static void main (String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

		String s = br.readLine(); 


		int freq[] = new int[256]; 
		int count = 0;  //How many unique characters 

		for(int i = 0; i<s.length(); i++){
			if(freq[(int)s.charAt(i)] == 0)
				count++; 

			freq[(int)s.charAt(i)]++; 
		}

		MinHeap q = new MinHeap(count); 

		for(int i = 0; i<256; i++){
			if(freq[i] != 0)
				q.add(freq[i], (char)i); 
		}

		String unComp = ""; 
		for(int i = 0; i<s.length(); i++){
			unComp += Integer.toBinaryString((int)s.charAt(i)); 
		}

		//System.out.println(unComp);



		//Apply compression 
		Node root = new Node(); 
		while(q.size() > 1){

			//Pop off the two nodes with the smallest frequencies 
			Node one = q.remove(); 
			Node two = q.remove(); 

			//Create a new Node with its data set to the sum of the data values from nodes one and two, char value doesn't matter 
			Node newNode = new Node((one.data + two.data), '#');  

			newNode.left = one;
			newNode.right = two;
			root = newNode;



			q.add(newNode); 
		}

		String arr[] = new String[256]; 
		printCodes(root, "", arr); 

		String compressedString = ""; 

		for(int i = 0; i<s.length(); i++){
			compressedString += arr[(int)s.charAt(i)];  
		}

		//System.out.println(compressedString); 
		decode(root, compressedString); 
		System.out.println(); 

		int originalStringSize = unComp.length(); 
		int compStringSize = compressedString.length(); 

		System.out.println("Original length of binary string " +originalStringSize); 
		System.out.println("Compressed binary string " +compStringSize); 

		System.out.println("Reduce by "+100*(double)compStringSize/originalStringSize +"%");
	}

	public static void printCodes(Node root, String code, String arr[]){

		//System.out.println(root.data); 
		if(root.left==null && root.right == null){
			//System.out.println("The code for " +root.c+ " is " +code); 
			arr[(int)root.c] = code; 
		}

		else{
			
			printCodes(root.left, code+"0", arr);
			printCodes(root.right, code+"1",arr); 
		}
	}

	public static void decode(Node root, String s){
	    Node iter = root; 

	    if(iter.left == null && iter.right == null){
	    	System.out.print(iter.c); 
	    	return; 
	    }

    
    	for(int i = 0; i<s.length(); i++){

        	if(s.charAt(i) == '0')
            	iter = iter.left; 

        	else
            	iter = iter.right; 

        	if(iter.left == null && iter.right == null){
            	System.out.print(iter.c); 
            	iter = root;  
        	}
    	}
	}
} 