import java.io.*;
import java.util.Stack;

public class CalculatorTest
{


	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;


				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
		// TODO : 아래 문장을 삭제하고 구현해라.
		myCalculator answer = new myCalculator(input);
		answer.printOutput();
	}



	static class myCalculator{
		private String infix;
		private String postfix;
		private long output;
		private boolean errFlag;


		public myCalculator(String input){
			this.infix = input;
			this.errFlag = false;
			this.makePostfix();
			this.calOutput();
		}

		public void seterrFlag(){
			this.errFlag = true;
		}
		public void setOutput(long a){
			this.output = a;
		}

		public String getInfix(){
			return this.infix;
		}
		public boolean geterrFlag(){
			return this.errFlag;
		}
		public String getPostfix(){
			return this.postfix;
		}
		public long getOutput(){
			return this.output;
		}

		public void printOutput() { // postfix, output 출력
			if(this.geterrFlag()){
				System.out.println("ERROR");
			}
			else{
				System.out.println(this.getPostfix());
				System.out.println(this.getOutput());
			}
		}

		public boolean isOpr(char a){ // Operator가 맞는지
			if( a == '+' || a == '-' || a == '*' || a == '/' || a == '%' || a == '^' || a == '(' || a == ')' ){
				return true;
			}
			else{
				return false;
			}
		}

		public boolean isNum(char a){ // 숫자가 맞는지
			if( a>='0' && a<='9'){
				return true;
			}
			else{
				return false;
			}
		}
		public int oprPriority(char a){ // operation priority
			if(a == '+' || a == '-'){
				return 1;
			}
			else if(a == '*' || a == '/' || a == '%'){
				return 2;
			}
			else if(a == '~'){
				return 3;
			}
			else if(a=='^'){
				return 4;
			}
			else if(a == '(' || a == ')'){
				return 5;
			}
			else{
				return 0;
			}
		}
		public void addNumOnPostfix(char ch){ // postfix 접근하는 메서드, 숫자넣을 땐 공백 들어가면 안되니까 opr이랑 따로 분리함
			if(this.postfix == null) { // null일 경우 += 말고 assign 해주기
				this.postfix = String.valueOf(ch);
			}
			else if(this.postfix.charAt(this.postfix.length()-1) < '0' || this.postfix.charAt(this.postfix.length()-1) > '9'){ // 기호를 넣고 난 후 숫자를 넣을 경우 공백 붙여준다.
				addOprOnPhosfix(' ');
				this.postfix += ch;
			}
			else{ // 숫자 계속 붙이기
				this.postfix += ch;
			}
		}
		public void addOprOnPhosfix(char ch){ // postfix 접근하는 메서드
			if(this.postfix == null){ // null일 경우 assign
				if(ch != ' '){
					this.postfix = String.valueOf(ch);
				}
			}
			else{
				if(ch == ' '){
					if(this.postfix.charAt(this.postfix.length()-1) != ' '){ // postfix의 마지막이 공백이 아닐 경우 공백 넣어주기 (공백입력시))
						this.postfix += ' ';
					}
				}
				else{
					if(this.postfix.charAt(this.postfix.length()-1) != ' '){ // postfix 마지막이 공백이 아닌 경우 구분(공백 이외)
						this.postfix += ' ';
						this.postfix += ch;
					}
					else{
						this.postfix += ch;
					}
				}
			}
		}
		public Stack<Character> stackPriority(Stack<Character> oprStack, char ch){ // stack에서 꺼내거나 넣는것 따로 메서드로
			if(!oprStack.empty()){
				if(ch==')'){
					while(!oprStack.empty()){
						if(oprStack.peek()=='('){
							break;
						}
						addOprOnPhosfix(oprStack.pop());
						if(oprStack.empty()){ // () 수 일치 안할 경우 err
							seterrFlag();
						}
					}
					if(!oprStack.empty()){
						oprStack.pop();
					}
					return oprStack;
				}
				else if(oprPriority(ch)>oprPriority(oprStack.peek())) { // 우선순위 높으면 스택에 넣어줌
					addOprOnPhosfix(' ');
					oprStack.push(ch);
					return oprStack;
				}
				else{
					if(oprStack.peek()=='('){ // (이면 그냥 넣어주기
						addOprOnPhosfix(' ');
						oprStack.push(ch);
						return oprStack;
					}
					else if(ch == '^' && oprStack.peek()=='^'){ // ^는 right-associate이므로 추가케이스 생성
						addOprOnPhosfix(' ');
						oprStack.push(ch);
						return oprStack;
					}
					else if(ch == '~' && oprStack.peek() == '~') { // ~는 right-associate이므로 추가케이스 생성
						addOprOnPhosfix(' ');
						oprStack.push(ch);
						return oprStack;
					}
					else{ // Priority가 더 낮거나 같을 경우 앞에걸 빼주고 다시 돌리기
						addOprOnPhosfix(oprStack.pop());
						return stackPriority(oprStack, ch);
					}
				}
			}
			else{ // 비어잇는 경우 그냥 넣어줌
				addOprOnPhosfix(' ');
				oprStack.push(ch);
				return oprStack;
			}
		}

		public boolean emptyParenthesisChecker(int a, int b, String C){ // 빈 괄호인지 체크(내부에 숫자있으면 err)
			int emptyCnt = 0;
			if(b-a==1){
				return true;
			}
			for(int i = a + 1; i < b; i++){
				if(C.charAt(i) >= '0' && C.charAt(i)<= '9'){
					emptyCnt++;
				}
			}
			if(emptyCnt !=0){
				return false;
			}
			else{
				return true;
			}
		}

		public void makePostfix() { // 계산
		//	System.out.println("infix: " + this.infix);
			Stack<Character> oprStack = new Stack<Character>();
			boolean unaryFlag = true;
			boolean numFlag = true;
			Stack<Integer> parenthesisStack = new Stack<Integer>(); // 빈 괄호에 대해서 err 넣어주기 위해 추가
			for(int i = 0; i < this.infix.length(); i++){
				char ch = this.infix.charAt(i);
	//			System.out.println("ch: " + ch);
				if(ch=='('){ // 빈 괄호에 대해서 err 넣어주기 위해 추가, err체크만 한다.
					parenthesisStack.push(i);
				}
				else if(ch==')'){
					if(!parenthesisStack.empty()){
						if(emptyParenthesisChecker(parenthesisStack.pop(), i, this.getInfix())){
							seterrFlag();
						}
					}
					else{
						seterrFlag();
						break;
					}
				}
				if(isNum(ch) && numFlag){ // 숫자면 그냥 넣기
					addNumOnPostfix(ch);
					unaryFlag = false;
				}
				else if(isOpr(ch) && !unaryFlag){ // unaryFlag가 false이고 operator 나오면 stack에 비교해서 넣기
					if(ch != ')'){ // )이 아닌 Operator가 나오면 그다음 -는 unary이다.
						unaryFlag = true;
						numFlag = true;
					}
					else{ // opr 다음에는 num이 와야한다.
						numFlag = false;
					}
					oprStack = stackPriority(oprStack, ch);

				}
				else if(isOpr(ch) && unaryFlag){ // unaryFlag가 true이면 uanry operator 또는 right-associate^ 가능
					if(ch == '-'){ // unary -
						ch = '~';
						oprStack = stackPriority(oprStack, ch);
					}
					else{
						if(ch == ')'){ // )이 아닌 Operator가 나오면 그다음 -는 unary이다.
							unaryFlag = false;
							numFlag = false;
						}
						else{
							numFlag = true;
						}
						oprStack = stackPriority(oprStack, ch);
					}
				}
				else if(ch == ' ' || ch == '\t'){ // 공백이면 그냥 넘어감, \t도 공백
					ch = ' ';
					addOprOnPhosfix(ch);
				}
				else{ // 이외의 입력에 대해서는 err
					this.seterrFlag();
				}

				if(this.geterrFlag() == true){ // error입력이면 나감
					break;
				}
			}
			while(!oprStack.empty()){
				addOprOnPhosfix(oprStack.pop());
			}
		}


		public void calOutput(){
			String tmp = null;
			Stack<Long> outputStack = new Stack<Long>();
			boolean breakFlag = false;
	//		System.out.println("postfix: " + this.postfix);

			if(this.geterrFlag()==false){
				for(int i = 0; i < this.getPostfix().length(); i++){
					char ch = this.getPostfix().charAt(i);
					if(ch >= '0' && ch <= '9'){ // 숫자 뽑아내기
						if(tmp == null){
							tmp = String.valueOf(ch);
						}
						else{
							tmp += String.valueOf(ch);
						}
					}
					else if(ch == ' '){ // 공백은 구분기호, long값을 stack에 넣기
						if(tmp!=null){
						
								outputStack.push(Long.parseLong(tmp, 10));

							tmp = null;
						}
					}
					else {
						switch(ch) { // 각 operator에 따른 계산방법
							case '^':
								if(outputStack.size()>1){
									long a = outputStack.pop();
									if(a < 0){
										seterrFlag();
										breakFlag = true;
										break;
									}
									long b = outputStack.pop();
									outputStack.push((long) Math.pow(b, a));
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							case '~':
								if(!outputStack.empty()){
									outputStack.push(outputStack.pop()*-1);
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							case '*':
								if(outputStack.size()>1){
									long a = outputStack.pop();
									long b = outputStack.pop();
									outputStack.push(b * a);
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							case '/':
								if(outputStack.size()>1){
									long a = outputStack.pop();
									if(a == 0){
										seterrFlag();
										breakFlag = true;
										break;
									}
									long b = outputStack.pop();
									outputStack.push(b / a);
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							case '%':
								if(outputStack.size()>1){
									long a = outputStack.pop();
									if(a == 0){
										seterrFlag();
										breakFlag = true;
										break;
									}
									long b = outputStack.pop();
									outputStack.push(b % a);
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							case '+':
								if(outputStack.size()>1){
									long a = outputStack.pop();
									long b = outputStack.pop();
									outputStack.push(b + a);
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							case '-':
								if(outputStack.size()>1){
									long a = outputStack.pop();
									long b = outputStack.pop();
									outputStack.push(b - a);
								}
								else{
									seterrFlag();
									breakFlag = true;
								}
								break;
							default:
								seterrFlag();
								breakFlag = true;
						}
					}
					if(breakFlag){
						break;
					}
				}
				if(tmp!=null){
					outputStack.push(Long.parseLong(tmp, 10));
				}
				if(outputStack.size()!=1){
					seterrFlag();
				}
				else{
					setOutput(outputStack.pop());
				}
			}


		}
	}
}
