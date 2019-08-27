import java.io.*;
import java.lang.Class;
import java.util.*;

// 최초 제출 파일
public class Subway{

	//	Node의 개수는 최대 만개라 가정할 때, 테이블의 크기는 넉넉하게 100189개로 함
	static dbTable db = new dbTable(100189);
	static NNLTable NNL = new NNLTable(100189);
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    File file = new File(args[0]);

    try{
      String line;
      Scanner scan = new Scanner(file);
      while(true){
				line = scan.nextLine();
				if(line.length()==0){
					break;
				}
				String[] parsed = line.split("\\s");
				NNL.add(parsed[0], parsed[1], parsed[2]);
				db.add(parsed[0], parsed[1], parsed[2]);
      }
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] parsed = line.split("\\s");
				String depName = NNL.searchName(parsed[0]);
				String arrName = NNL.searchName(parsed[1]);
				long distVal = 0;
				for(int i = 0; i < parsed[2].length(); i++){
					distVal *= 10;
					distVal += parsed[2].charAt(i) - '0';
				}
				db.search(depName, parsed[0]).addEdge(db.search(arrName, parsed[1]), distVal);
      }
    } catch(FileNotFoundException e){
      System.err.println("cannot find file");
    }

		while (true){
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				find(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	/*
	*		잘못된 입력은 없다고 가정,
	*		출발지가 환승역일 수 있으므로 출발지 리스트를 모두 초기화 한 뒤, 각 노드의 Edge들의 도착역에 출발 노드의 Distance와 Edge의 Weight을 더한 값을 넣어주고,
	*		이 값을 기준으로 오름차순으로 정렬하는 PriorityQueue에 넣어준다.이 PriorityQueue를 dijkstraCandidate라고 할 때,
	*		마킹되지 않은 노드들 중 마킹된 노드들과 연결되는 노드들은 모두 PriorityQueue에 속하게 되고 출발지점을 기준으로 거리순으로 정렬된다.
	*		가장 가까운 노드를 꺼낸 후 마킹여부를 확인하고 마킹되지 않은 경우 마킹한 뒤 Path값을 Update한 후 도착역과 연결된 Edge들을 이용하여 PriorityQueue에 넣는 과정을 반복한다.
	*		꺼낸 노드의 도착역이 최종 도착역과 같은 경우 path와 distance를 출력(greedy Algorithm이기 때문에 dijkstra가 완료된 노드는 값이 변할 수 없음)
	*/
	private static void find(String input){
		String[] parsed = input.split("\\s");
		ArrayList<subwayNode> departureList = db.search(parsed[0]);
		String arrival = parsed[1];
		db.init();
		PriorityQueue<subwayNode> dijkstraCandidate = new PriorityQueue<subwayNode>();
		for(int itr = 0; itr < departureList.size(); itr++){
			subwayNode departure = departureList.get(itr);
			departure.initDistance();
			departure.setMark();
			for(Edge candidate : departure.getEdge()){
				candidate.getArrNode().setDistance(departure.getDistance() + candidate.getWeight(), departure); // relaxation
				dijkstraCandidate.add(candidate.getArrNode());
			}
		}
		while(!dijkstraCandidate.isEmpty()){ // 만약 없을경우 무한루프 방지
			subwayNode nextCandidate = dijkstraCandidate.poll();
			if(!nextCandidate.getMark()){
				nextCandidate.setMark();
				nextCandidate.setTransfer(nextCandidate.addPath(nextCandidate.getFromNode().getPath(), nextCandidate.getFromNode().getName(), nextCandidate.getFromNode().getLine(), nextCandidate.getFromNode().getTransfer()));
        if(nextCandidate.getName().compareTo(arrival)==0){
					System.out.println(nextCandidate.getPath() + arrival);
					System.out.println(nextCandidate.getDistance());
					break;
				}
        for(Edge newCandidate : nextCandidate.getEdge()){
					newCandidate.getArrNode().setDistance(nextCandidate.getDistance() + newCandidate.getWeight(), nextCandidate); // relaxation
					dijkstraCandidate.add(newCandidate.getArrNode());
				}
			
			}
		}
	}
}

/*
*		Number-Name-Line 찾아주는 Table, Input data중 number는 겹치는게 없기 때문에 number를 key로 활용
*		hashingKey는 각 자리의 문자값 * 10^(자릿수)로 하였고, 음수인 경우 not연산을 통해 양수로 변환, 겹칠 경우 quadratic resolution 사용
*/
class NNLTable{
  String [] numBucket;
  String [] nameBucket;
  String [] lineBucket;
  int hashNum;
  public NNLTable(int hashNum){
    this.hashNum = hashNum;
    numBucket = new String[hashNum];
    nameBucket = new String[hashNum];
    lineBucket = new String[hashNum];
  }
  public boolean isEmpty(int index){
    return numBucket[index] == null;
  }
  public int hashingKey(String key){
    int hashedKey = 0;
    for(int i = 0; i < key.length(); i++){
      hashedKey *= 10;
      hashedKey += (key.charAt(i));
    }
    if(hashedKey<0){
      hashedKey = ~hashedKey;
    }
    return (hashedKey % this.hashNum);
  }
  public int collisionKey(int index, int times){
    int nextindex = index + times * times;
    if(nextindex<0){
      nextindex = ~nextindex;
    }
    return (nextindex % this.hashNum);
  }
  public void add(String num, String name, String line){
    int index = hashingKey(num);
    int i = 0;
    while(true){
      index = collisionKey(index, i);
      if(numBucket[index] == null){
        numBucket[index] = new String(num);
        nameBucket[index] = new String(name);
        lineBucket[index] = new String(line);
        break;
      }
      i++;
    }
  }
  public String searchName(String key){
    int index = hashingKey(key);
    int i = 0;
    while(true){
      index = collisionKey(index, i);
      try{
        if(numBucket[index].compareTo(key)==0){
          return nameBucket[index];
        }
      } catch(NullPointerException e){
        System.err.println("NPE at NNLTable searchName" + key);
        return null;
      }
      i++;
    }
  }
  public String searchLine(String key){
    int index = hashingKey(key);
    int i = 0;
    while(true){
      index = collisionKey(index, i);
      try{
        if(numBucket[index].compareTo(key)==0){
          return lineBucket[index];
        }
      } catch(NullPointerException e){
        System.err.println("NPE at NNLTable searchLine" + key);
        return null;
      }
      i++;
    }
  }
}

/*
*		db 저장하는 Table, 이름을 key값으로 사용, 환승역때문에 유일하지 않아 nodeBucket은 ArrayList로 구현
*		hashingKey는 각 자리의 문자값 * 10^(자릿수)로 하였고, 음수인 경우 not연산을 통해 양수로 변환, 겹칠 경우 quadratic resolution 사용
*/
class dbTable{
  String [] nameBucket;
  ArrayList [] nodeBucket;
  int hashNum;

  public dbTable(int hashNum){
    this.hashNum = hashNum;
    nameBucket = new String[hashNum];
    nodeBucket = new ArrayList[hashNum];
  }

  public boolean isEmpty(int index){
    return nameBucket[index] == null;
  }

  public void init(){
    for(int i = 0 ; i <hashNum; i++){
      ArrayList<subwayNode> tmp = (ArrayList<subwayNode>)nodeBucket[i];
      if(tmp!=null){
        for(int j = 0; j < tmp.size(); j++){
					tmp.get(j).init();
				}
      }
    }
  }

  public int hashingKey(String key){
    int hashedKey = 0;
    for(int i = 0; i < key.length(); i++){
      hashedKey *= 10;
      hashedKey += (key.charAt(i));
    }
    if(hashedKey<0){
      hashedKey = ~hashedKey;
    }
    return hashedKey % hashNum;
  }
  public int collisionKey(int index, int times){
    int nextindex = index + times * times;
    if(nextindex<0){
      nextindex = ~nextindex;
    }
    return nextindex % hashNum;
  }
  public void add(String num, String name, String line){
    int index = hashingKey(name);
    int i = 0;
    while(true){
      index = collisionKey(index, i);
      if(nameBucket[index] == null){
        nameBucket[index] = new String(name);;
        nodeBucket[index] = new ArrayList<subwayNode>();
				nodeBucket[index].add(new subwayNode(name, num, line));
        break;
      }
      else if(nameBucket[index].compareTo(name)==0){ //환승역 사이에 weight이 5인 node들 추가하기
        nodeBucket[index].add(new subwayNode(name, num, line));
				for(int j = 0; j < nodeBucket[index].size()-1; j++){
					((subwayNode)nodeBucket[index].get(j)).addEdge(((subwayNode)nodeBucket[index].get(nodeBucket[index].size()-1)), 5);
					((subwayNode)nodeBucket[index].get(nodeBucket[index].size()-1)).addEdge((subwayNode)nodeBucket[index].get(j), 5);
				}
				break;
      }
      i++;
    }
  }
	public subwayNode search(String name, String num){
    int index = hashingKey(name);
    int i = 0;
    while(true){
      index = collisionKey(index, i);
      try{
        if(nameBucket[index].compareTo(name)==0){
					for(int j = 0; j < nodeBucket[index].size(); j++){
						if(((subwayNode)nodeBucket[index].get(j)).getNum().compareTo(num)==0){
							return (subwayNode)nodeBucket[index].get(j);
						}
					}
        }
      } catch(NullPointerException e){
        System.err.println("NPE at dbTable searching key");
        return null;
      }
      i++;
    }
  }
	public ArrayList<subwayNode> search(String name){
    int index = hashingKey(name);
    int i = 0;
    while(true){
      index = collisionKey(index, i);
      try{
        if(nameBucket[index].compareTo(name)==0){
          return nodeBucket[index];
        }
      } catch(NullPointerException e){
        System.err.println("NPE at dbTable searching key");
        return null;
      }
      i++;
    }
  }
}

/*
*		각 Node들, Edge는 중복으로 가질 수 있으므로 관리의 편의를 위해 ArrayList로 함
*		환승일 경우를 대비해 transfer 넣음, PriorityQueue를 이용해 마킹되지 않은 집단 중 가장 작은 distance값을 가지는 역을 가져옴
*		Path값을 update해주기 위해 이전 역 정보 저장
*		역이름, 역번호, 호선 정보를 저장함, 역 이름이 같은 노드(환승역)들이 있어서 이름과 번호를 모두 확인해야하므로 다 넣음
*		간선의 수 최대 수십만개, 간선의 소요시간 1억 이하이므로 최초 distance는 100조로 초기화(1억*100만)
*/
class subwayNode implements Comparable<subwayNode>{
  ArrayList<Edge> nextNode;
	subwayNode fromNode;
  boolean mark;
	boolean transfer;
  String path;
  long distance;
  String stName;
  String stNum;
  String stLine;

  public subwayNode(){
    nextNode = new ArrayList<Edge>();
    mark = false;
		transfer = false;
    path = "";
    distance = 100000000000000L;
    stName = null;
    stNum = null;
    stLine = null;
		fromNode = null;
  }
	public subwayNode(String name, String num, String line){
		nextNode = new ArrayList<Edge>();
		mark = false;
		transfer = false;
		path = "";
		distance = 100000000000000L;
		stName = name;
		stNum = num;
		stLine = line;
		fromNode = null;
	}


  public void setMark(){
    this.mark = true;
  }
  public void setDistance(long dist, subwayNode from){
    if(this.mark==false){
			if(this.distance > dist){
				this.distance = dist;
				this.fromNode = from;
			}
    }
  }
	public void setTransfer(boolean trans){
		this.transfer = trans;
	}
	public void setFromNode(subwayNode depnode){
		this.fromNode = depnode;
	}
  public void init(){
    this.mark = false;
		this.transfer = false;
    this.path = "";
    this.distance = 100000000000000L;
  }

  public void initDistance(){
    this.distance = 0;
  }

  public String getPath(){
    return this.path;
  }
  public boolean getMark(){
    return this.mark;
  }
	public boolean getTransfer(){
		return this.transfer;
	}
	public subwayNode getFromNode(){
		return this.fromNode;
	}
  public String getNum(){
    return this.stNum;
  }
  public String getLine(){
    return this.stLine;
  }
  public String getName(){
    return this.stName;
  }
  public ArrayList<Edge> getEdge(){
    return this.nextNode;
  }
  public long getDistance(){
    return this.distance;
  }

  public boolean addPath(String pastPath, String paststName, String paststLine, boolean trans){
    path = pastPath;
		if(trans){ // 이전 역이 환승역일 경우 path 업데이트 안함
			return false;
		}
		else if(paststName.compareTo(stName)==0){ // 환승역일 경우 trans를 반환값을 통해 true로 바꿔주기
			path += "[" + paststName + "] ";
			return true;
		}
    else if(paststLine.compareTo(stLine)==0 || paststLine.compareTo("")==0){
      path += paststName;
    }
    else{
      path += "[" + paststName + "]";
    }
    path += " ";
		return false;
  }

  public void addEdge(subwayNode arrNode, long weight){
    Edge newEdge = new Edge(arrNode, weight);
    this.nextNode.add(newEdge);
  }
	@Override
	public int compareTo(subwayNode other){
		 return Long.compare(this.distance, other.getDistance());
	}
}

/*
*		Edge는 도착역과 weight값을 가짐, 이 값은 최초 입력 이후 변하지 않음
*		Edge를 PriorityQueue로 사용하려면 Edge가 distance를 가지거나 매번 이전역의 distance를 한번 더 호출해야하므로 subwayNode를 priorityqueue에 사용함.
*/
class Edge{
  subwayNode arrNode;
	long weight;

  public Edge(subwayNode arrnode, long wei){
		this.arrNode = arrnode;
		this.weight = wei;

  }
	public subwayNode getArrNode(){
    return this.arrNode;
  }
	public long getWeight(){
		return this.weight;
	}

}
