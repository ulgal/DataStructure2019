import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  

public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
  
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    //private Data
    private byte[] BigInt;
    private int sign;

  

    //constructors
    public BigInteger() {
    	
    }
    public BigInteger(int sign)
    {
    	setSign(sign);
    }  
    public BigInteger(String arg)
    {
    	StringtoByteArray(arg);
    }
    public BigInteger(String arg, int sign)
    {
    	StringtoByteArray(arg);
    	setSign(sign);
    }
    public BigInteger(byte[] num, int sign) {
    	setSign(sign);
    	BigInt = num;
    }
    
    //Methods
    public void StringtoByteArray(String arg) {
    	if(arg.length()!=0) {
    		byte[] TfBigInt = new byte[arg.length()];
    	   	for (int i = 0; i < arg.length(); i++)
    	   	{
    	   	    TfBigInt[i] = (byte) (arg.charAt(arg.length()-i-1) - '0');
    	   	}
    	   	BigInt = TfBigInt;
    	}
    }// String 안에 있는 숫자들 byte 배열 상에 거꾸로 집어넣기(곱셉 편의를 위해)
    
    public void setBigInt(byte[] num) {
    	BigInt = num;
    }
    public void setBigInt(byte[] num, int signbit) {
    	BigInt = num;
    	sign = signbit;
    }
    public void setBigInt(byte num, int index) {
    	BigInt[index]=num;
    }
    public void setBigInt(BigInteger num) {
    	BigInt = num.BigInt;
    	sign = num.sign;
    }
    public void setSign(int signbit) {
    	sign = signbit;
    }
    public byte[] getBigInt() {
    	return BigInt;
    }
    public byte getBigInt(int index) {
    	return BigInt[index];
    }
    public int getSign() {
    	return sign;
    }
    
    public boolean comparison(byte[] num1, byte[] num2, int num1length, int num2length) {
    	if(num1length>num2length||(num1length==num2length&&num1[num1length-1]>num2[num2length-1])) {
    		return true;
    	}
    	else if(num1length<num2length||(num1length==num2length&&num1[num1length-1]<num2[num2length-1])) {
    		return false;
    	}
    	else if((num1length-1)==0){
    		return true;
    	}//두 숫자가 같은 케이스
    	else {
    		return comparison(num1, num2, num1length-1,num2length-1);
    	}  	
    } // 두 숫자중 뭐가 더 큰지 알려주는 method, recursive
    
    public byte[] reArrangeAS(byte[] transnum) {
   		byte[] aftertrans = new byte[transnum.length+1];
   	   	for (int i = 0; i < transnum.length; i++)
   	   	{//여기 edge case 점검!
   	   		if(transnum[i]<0) {  
   	   			transnum[i+1] -= 1;
   	   			transnum[i] += 10;
   	   			aftertrans[i] = transnum[i];
   	   		}
   	   		else if(transnum[i]>9&&i==transnum.length-1) {  
   	   			transnum[i] -=10;
   	   			aftertrans[i] = transnum[i];
   	   			aftertrans[i+1] = 1;
   	   		}
   	   		else if(transnum[i]>9) {  
   	   			transnum[i] -= 10;
   	   			transnum[i+1] += 1;
   	   			aftertrans[i] = transnum[i];
   	   		}
   	   		else {
   	   			aftertrans[i]=transnum[i];
   	   		}
   	   	}
   	   	return aftertrans;
    } // 덧셈, 뺄셈 큰수-작은수 연산에서 각 자릿수 10 이상 또는 음수인 부분 없애주기 
    
   
    public BigInteger calculating(BigInteger num, int calMarker) {
    	BigInteger calResult = new BigInteger();
    	if(comparison(this.getBigInt(), num.getBigInt(), this.BigInt.length, num.BigInt.length)) {
    		if(this.getSign()==1) {
    			calResult.setSign(1);
    		}
    		else {
    			calResult.setSign(-1);
    		}
    		if(calMarker*this.getSign()*num.getSign()==1) {
    			for(int i = 0; i< num.BigInt.length;i++) {
    				this.setBigInt((byte)(this.getBigInt(i)+num.getBigInt(i)), i);
    			}
    		}
    		else {
    			for(int i = 0; i< num.BigInt.length;i++) {
    				this.setBigInt((byte)(this.getBigInt(i)-num.getBigInt(i)), i);
    			}
    		}
    		calResult.setBigInt(reArrangeAS(this.getBigInt()));
    	}
    	else {
    		if(num.getSign()*calMarker==1) {
    			calResult.setSign(1);
    		}
    		else {
    			calResult.setSign(-1);
    		}
    		if(calMarker*this.getSign()*num.getSign()==1) {
    			for(int i = 0; i< this.BigInt.length;i++) {
    				num.setBigInt((byte)(num.getBigInt(i)+this.getBigInt(i)), i);
    			}
    		}
    		else {
    			for(int i = 0; i< this.BigInt.length;i++) {
    				num.setBigInt((byte)(num.getBigInt(i)-this.getBigInt(i)), i);
    			}
    		}
    		calResult.setBigInt(reArrangeAS(num.getBigInt()));
    	}
    	return calResult;
    }// add와 subtract는 유사해서 case별로 나눠 계산하는 method 하나 만듬.

    
    public BigInteger add(BigInteger big)
    {   
    	BigInteger addResult = new BigInteger();
    	addResult.setBigInt(this.calculating(big, 1));
    	return addResult;
    }// add는 marker 1
  
    public BigInteger subtract(BigInteger big)
    {
    	BigInteger subResult = new BigInteger();
    	subResult.setBigInt(this.calculating(big, -1));
    	return subResult;
    }// subtract는 marker -1
  
    public BigInteger multiply(BigInteger big)
    {
    	byte[] tempMul = new byte[this.BigInt.length+big.BigInt.length];
    	for(int i = 0; i < this.BigInt.length; i++) {
    		for(int j = 0; j < big.BigInt.length; j++) {
    			tempMul[i+j] += (byte) (this.getBigInt(i) * big.getBigInt(j));
    			if(tempMul[i+j]>9) {
    				tempMul[i+j+1] += tempMul[i+j]/10;
    				tempMul[i+j] = (byte) (tempMul[i+j]%10); 
    			}
    		}
    	}
    	BigInteger mulResult = new BigInteger(tempMul, this.getSign()*big.getSign());
    	mulResult.setSign(this.getSign()*big.getSign());
    	return mulResult;
    } 
    @Override
    public String toString()
    {
    	String stringResult = new String();
    	int changeCnt = this.BigInt.length - 1;
    	while(true) {
    		if(this.getBigInt(changeCnt)==0&&changeCnt>0) {
    			changeCnt--;
    		}
    		else break;
    	}
    	if(this.getBigInt(changeCnt)==0) {
    		stringResult += "0";
    		return stringResult;
    	}
    	else {
    		if(this.getSign()==-1) {
    			stringResult += "-";
    		}
    		while(changeCnt>=0) {
    			stringResult += this.getBigInt(changeCnt);
    			changeCnt--;
    		}
    	}
    	return stringResult;
    } // 음수인 경우 - 삽입, 앞에서부터 0 제외, 이후 숫자 하나씩 집어넣기, 0인 경우 그냥 0만 출력(- 출력 x)
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
    	// using regex is allowed
    	String regex = "([\\s]*)([+|-]{0,1})([\\s]*)([0-9]{1,100})([\\s]*)([+|*|-]{1})([\\s]*)([+|-]{0,1})([\\s]*)([0-9]{1,100})([\\s]*)?\\Z";
    	// JAVA 정규식 표현: https://nesoy.github.io/articles/2018-06/Java-RegExp
    	
    	Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(input);
	    /*
		matcher.group(n):
		2 == sign1
		8 == sign2
		4 == arg1
		10 == arg2
		6 == oprSign
		0 == 전체식
		그외 == blink
	    */

	    matcher.find(); // 이거 안하면 왠지 모르지만 matcher.group()에서 에러남
	    int sign1 = 1;
	    int sign2 = 1;
	    
	    if(matcher.group(2).length()!=0) {
	    	if(matcher.group(2).contains("-")) {
	    		sign1 = -1;
	    	}
	    	else {
	    		sign1 = 1;
	    	}
	    }
	    if(matcher.group(8).length()!=0) {
	    	if(matcher.group(8).contains("-")) {
	    		sign2 = -1;
	    	}
	    	else {
	    		sign2 = 1;
	    	}
	    }
	    String arg1 = matcher.group(4);
	    String arg2 = matcher.group(10);    
	    char oprSign = matcher.group(6).charAt(0);
	    
	    //Arithmetic Operation
        BigInteger num1 = new BigInteger(arg1, sign1);
        BigInteger num2 = new BigInteger(arg2, sign2);
        BigInteger result = new BigInteger();
        if(oprSign == '+') {
           result = num1.add(num2);
        }else if(oprSign == '-') {
            result = num1.subtract(num2);
        }else if(oprSign == '*') {
            result = num1.multiply(num2);
        }
       return result;
    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());

            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}