import java.io.*;
import java.lang.Class;
import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

public class Matching{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		UgHashTable<String, CoordinateValue> db = new UgHashTable<String, CoordinateValue>(100);

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input, db);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	/*
	*		잘못된 입력은 없다고 가정, input의 첫 문자에 따라 어떤 command 실행할 지 결정
	*		잘못된 입력은 없다고 가정, 첫 문자와 공백을 제외한 input값을 parse함
	*		이후 미리 크기를 100으로 선언한 UgHashTable에 대해 command 수행
	*/
	private static void command(String input, UgHashTable<String, CoordinateValue> db)
	{
		if (input.startsWith("<")) {
			ReadFileCmd command = new ReadFileCmd();
			command.parse(input.substring(2));
			command.apply(db);
		} else if (input.startsWith("@")) {
			PrintTreeCmd command = new PrintTreeCmd();
			command.parse(input.substring(2));
			command.apply(db);
		} else if (input.startsWith("?")) {
			SearchPatternCmd command = new SearchPatternCmd();
			command.parse(input.substring(2));
			command.apply(db);
		}





	}

	/******************************************************************************
	 * < (FILENAME)
	 */
	static class ReadFileCmd {
		private String fileName;


		public void parse(String instrC){
		   //예외처리 필요한 부분
	     this.fileName = instrC;
		}

		/*
		*		파일 스캔 이후 6개 단위로 문자열 UgHashTable에 넣어준다.
		*/
		public void apply(UgHashTable<String, CoordinateValue> db){
			db.removeAll();
			File file = new File(this.fileName);
			try{
				Scanner scan = new Scanner(file);
				int column = 1;
				while(scan.hasNextLine()){
					String line = scan.nextLine();
					for(int i = 0; i < line.length() - 5; i++){
						db.add(line.substring(i, i+6), new CoordinateValue(column, i+1));
					}
					column++;
				}
			}catch(FileNotFoundException e){
				;
			}



		}


	}

	/******************************************************************************
	 * @ (INDEX NUMBER)
	 */
	static class PrintTreeCmd {
		private Integer indexNum;


		protected void parse(String instrC) {
			//예외처리 필요한 부분
	    this.indexNum = Integer.parseInt(instrC);
		}

		/*
		* 입력받은 index의 Tree를 받아 이를 출력
		*/
		public void apply(UgHashTable<String, CoordinateValue> db){
			db.get(this.indexNum).print();
		}


	}


	/******************************************************************************
	 * ? (PATTERN)
	 */
	static class SearchPatternCmd {
		private ArrayList<String> pattern;


		protected void parse(String instrC) {
			//예외처리 필요한 부분
			this.pattern = new ArrayList<String>();
	    for(int i = 0; i < instrC.length() - 5; i++){
				pattern.add(instrC.substring(i, i+6));
			}
		}

		/*
		* 	길이가 6 이상인 Pattern size만큼의 배열에 각 문자열을 담는 UgLinkedList 저장
		* 	이후 연속인지 확인
		*/
		public void apply(UgHashTable<String, CoordinateValue> db){
			UgLinkedList<CoordinateValue>[] checkbox = new UgLinkedList[pattern.size()];
			for(int i = 0; i < pattern.size(); i++){
				checkbox[i] = db.search(pattern.get(i).toString());

			}
			continuous(checkbox);



		}

		/*
		* 	배열의 첫 원소 LinkedList의 첫 data를 stack에 넣는다
		* 	이후 그 다음 배열 원소의 LinkedList를 순차적으로 돌면서
		*		Stack의 peek와 연속하는지 확인한다.
		* 	연속한다면 push, 그렇지 않다면 다음 원소 확인, 끝까지 가면 backtraking
		* 	배열의 크기만큼 stack이 차면 스택의 가장 아래에 있는 좌표를 출력한 후 pop, printFlag++;
		*		배열의 첫 원소 LinkedList의 다음 data를 stack에 넣는방식
		*/
		public void continuous(UgLinkedList<CoordinateValue> [] checkbox){
			int printFlag = 0;
			int searchFailFlag = 0;
			for(int i = 0; i < checkbox.length; i++){
				if(checkbox[i]==null){
					searchFailFlag++;
				}
			}
			if(searchFailFlag==0){
				Node<CoordinateValue> checker = checkbox[0].getHead();
				Stack<CoordinateValue> box = new Stack<CoordinateValue>();
				while(checker.hasNext()){
					checker = checker.getNext();
					box.add(checker.getItem());
					for(int i = 1; i < checkbox.length; i++){
						if(box.peek().continuous(checkbox[i])!=null){
							box.add(box.peek().continuous(checkbox[i]));
						}
						else{
							break;
						}
					}
					if(box.size()==checkbox.length){
						while(box.size()>1){
							box.pop();
						}
						if(printFlag!=0){
							System.out.print(" ");
						}
						box.peek().printCoordinateValue();
						box.pop();
						printFlag++;
					}
					else{
						while(!box.isEmpty()){
							box.pop();
						}
					}
				}
			}
			if(printFlag==0||searchFailFlag!=0){
				System.out.print("(0, 0)");
			}
			System.out.println();
		}
	}
}
