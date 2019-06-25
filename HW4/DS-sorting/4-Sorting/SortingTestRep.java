import java.io.*;
import java.util.*;

public class SortingTestRep
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
					//for (int i = 0; i < newvalue.length; i++)
					//{
					//	System.out.println(newvalue[i]);
					//}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		for(int i = value.length - 1 ; i >= 0 ; i--){
			for(int j = 0; j < i; j++){
				if(value[j]>value[j+1]){
					int tmp = value[j];
					value[j] = value[j+1];
					value[j+1] = tmp;
				}
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.
		for(int i = 1 ; i < value.length ; i++){
			int j = i-1;
			int tmp = value[i];
			while(j>=0){ // stable sorting
				if(value[j]>tmp){
					value[j+1] = value[j];
					j--;
				}
				else{
					break;
				}
			}
			value[j+1] = tmp;
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		if(value.length == 0){
			return null;
		}
		// TODO : Heap Sort 를 구현하라.
		for(int i = (value.length / 2); i > 0; i--){
			value = percolateDown(value, i, value.length);
		}
		for(int i = value.length; i > 1 ; i--){
			int tmp = value[0];
			value[0] = value[i-1];
			value[i-1] = tmp;
			value = percolateDown(value, 1, i-1);
		}
		return value;
	}

	private static int[] percolateDown(int[] value, int i, int n){
		int lChild = 2*i;
		int rChild = 2*i + 1;
		if(lChild <= n){
			if((rChild <= n)&&(value[lChild-1]<value[rChild-1])){
				lChild = rChild;
			}
			if(value[i-1]<value[lChild-1]){
				int tmp = value[i-1];
				value[i-1] = value[lChild-1];
				value[lChild-1] = tmp;
				value = percolateDown(value, lChild, n);
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		if(value.length > 1){
			int i = value.length / 2;
			int[] s1 = new int[i];
			System.arraycopy(value, 0, s1, 0, i);
			int[] s2 = new int[value.length - i];
			System.arraycopy(value, i, s2, 0, value.length - i);
			s1 = DoMergeSort(s1);
			s2 = DoMergeSort(s2);
			value = DoMerge(s1, s2);
		}
		return value;
	}

	private static int[] DoMerge(int[] s1, int[] s2){
		int indexS1 = 0;
		int indexS2 = 0;
		int indexValue = 0;
		int[] value = new int[s1.length + s2.length];
		while(indexS1<s1.length || indexS2<s2.length){
			if(indexS1>=s1.length){
				while(indexS2<s2.length){
					value[indexValue] = s2[indexS2];
					indexValue++;
					indexS2++;
				}
			}
			else if(indexS2>=s2.length){
				while(indexS1<s1.length){
					value[indexValue] = s1[indexS1];
					indexValue++;
					indexS1++;
				}
			}
			else {
				if(s1[indexS1]<s2[indexS2]){
					value[indexValue] = s1[indexS1];
					indexS1++;
				}
				else{
					value[indexValue] = s2[indexS2];
					indexS2++;
				}
				indexValue++;
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		if(value.length>1){
			int p = value.length / 2;
			int tmpP = value[p];
			value[p] = value[value.length - 1];
			value[value.length - 1] = tmpP;
			int finger1 = 0;
			for(int finger2 = 0; finger2 < value.length - 1; finger2++){
				if(value[finger2]<value[value.length - 1]){
					int tmpSwap = value[finger1];
					value[finger1] = value[finger2];
					value[finger2] = tmpSwap;
					finger1++;
				}
			}
			tmpP = value[finger1];
			value[finger1] = value[value.length - 1];
			value[value.length - 1] = tmpP;
			System.arraycopy(DoQuickSort(Arrays.copyOf(value, finger1)), 0, value, 0, finger1);
			System.arraycopy(DoQuickSort(Arrays.copyOfRange(value, finger1 + 1, value.length)), 0, value, finger1 + 1, value.length - finger1 - 1);
			//int[] s1 = new int[finger1];
			//System.arraycopy(value, 0, s1, 0, finger1);
			//int[] s2 = new int[value.length - finger1 - 1];
			//System.arraycopy(value, finger1+ 1, s2, 0, value.length - finger1 - 1);
			//System.arraycopy(DoQuickSort(s1), 0, value, 0, finger1);
			//System.arraycopy(DoQuickSort(s2), 0, value, finger1 + 1, value.length - finger1 - 1);
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		int indexBinary = 0x00000001;
		int rxValue[] = new int[value.length];
		for(int i = 0; i < 31; i++){
			int indexRx = 0;
			for(int j = 0; j < value.length;  j++){
				if((value[j]&indexBinary)==0){
					rxValue[indexRx] = value[j];
					indexRx++;
				}
			}
			for(int j = 0; j < value.length;  j++){
				if((value[j]&indexBinary)!=0){
					rxValue[indexRx] = value[j];
					indexRx++;
				}
			}
			indexBinary *= 2;
			System.arraycopy(rxValue, 0, value, 0, value.length);
		}
		indexBinary = 0x80000000;
		int indexRx = 0;
		for(int j = 0; j < value.length;  j++){
			if((value[j]&indexBinary)!=0){
				rxValue[indexRx] = value[j];
				indexRx++;
			}
		}
		for(int j = 0; j < value.length;  j++){
			if((value[j]&indexBinary)==0){
				rxValue[indexRx] = value[j];
				indexRx++;
			}
		}
		value = rxValue;
		return value;
	}
}
