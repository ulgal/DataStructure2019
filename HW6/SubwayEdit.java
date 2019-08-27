import java.io.*;
import java.lang.Class;
import java.util.*;

// 수정 파일
public class SubwayEdit{

	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner scan = null;
    try{
       scan = new Scanner(new File(args[0]));  
    } catch(FileNotFoundException e){
      System.err.println("cannot find file");
    }
    String line;
    HashMap<String, String> numtoName = new HashMap<String, String>();
    HashMap<String, ArrayList<SubwayNode>> db = new HashMap<String, ArrayList<SubwayNode>>();
    while(scan.hasNextLine()){
      line = scan.nextLine();
      if(line.length()==0){
        break;
      }
      var parsed = line.split(" ");
      numtoName.put(parsed[0], parsed[1]);
      if(db.containsKey(parsed[1])){
        SubwayNode addedSubwayNode = new SubwayNode(parsed[1], parsed[0], parsed[2]);
        ArrayList<SubwayNode> ArrList = db.get(parsed[1]);
        for(var transferStation : ArrList){
          transferStation.addEdge(addedSubwayNode, 5);
          addedSubwayNode.addEdge(transferStation, 5);
        }
        ArrList.add(addedSubwayNode);
      }
      else{
        SubwayNode newSubwayNode = new SubwayNode(parsed[1], parsed[0], parsed[2]);
        ArrayList<SubwayNode> newArrList = new ArrayList<SubwayNode>();
        newArrList.add(newSubwayNode);
        db.put(parsed[1], newArrList);
      }
    }
    while(scan.hasNextLine()){
      line = scan.nextLine();
      var parsed = line.split(" ");
      var depName = numtoName.get(parsed[0]);
      var arrName = numtoName.get(parsed[1]);
      long distVal = Long.parseLong(parsed[2]);
      ArrayList<SubwayNode> addEdgeList = db.get(depName);
      ArrayList<SubwayNode> addedEdgeList = db.get(arrName);
      
      for(var adds : addEdgeList){
        if(adds.getNum().compareTo(parsed[0])==0){
          for(var addeds : addedEdgeList){
            if(addeds.getNum().compareTo(parsed[1])==0){
              adds.addEdge(addeds, distVal);
              break;
            }
          }
        }
      }
      
    }

		while (true){
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
          break;

				find(input, db);
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
  *   이후 최초 상태에서 바뀐 SubwayNode들 초기화
	*/
	private static void find(String input, HashMap<String, ArrayList<SubwayNode>> db){
    var parsed = input.split(" ");  
		ArrayList<SubwayNode> departureList = db.get(parsed[0]);
		String arrival = parsed[1];
    PriorityQueue<SubwayNode> dijkstraCandidate = new PriorityQueue<SubwayNode>();
    Stack<SubwayNode> forinit = new Stack<SubwayNode>();
		for(int itr = 0; itr < departureList.size(); itr++){
			SubwayNode departure = departureList.get(itr);
      forinit.add(departure);
      departure.initDistance();
      departure.setMark();
			for(Edge candidate : departure.getEdge()){
				candidate.getArrNode().setDistance(departure.getDistance() + candidate.getWeight(), departure); // relaxation
        dijkstraCandidate.add(candidate.getArrNode());
        forinit.add(candidate.getArrNode());
      }
		}
		while(!dijkstraCandidate.isEmpty()){ // 만약 없을경우 무한루프 방지
      SubwayNode nextCandidate = dijkstraCandidate.poll();
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
          forinit.add(newCandidate.getArrNode());
        }
			}
    }
    while(!forinit.isEmpty()){
      SubwayNode initDijkstra = forinit.pop();
      initDijkstra.init();
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
class SubwayNode implements Comparable<SubwayNode>{
  ArrayList<Edge> nextNode;
	SubwayNode fromNode;
  boolean mark;
	boolean transfer;
  String path;
  long distance;
  String stName;
  String stNum;
  String stLine;

  public SubwayNode(){
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
	public SubwayNode(String name, String num, String line){
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
  public void setDistance(long dist, SubwayNode from){
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
	public void setFromNode(SubwayNode depnode){
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
	public SubwayNode getFromNode(){
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

  public void addEdge(SubwayNode arrNode, long weight){
    Edge newEdge = new Edge(arrNode, weight);
    this.nextNode.add(newEdge);
  }
	@Override
	public int compareTo(SubwayNode other){
		 return Long.compare(this.distance, other.getDistance());
	}
}

/*
*		Edge는 도착역과 weight값을 가짐, 이 값은 최초 입력 이후 변하지 않음
*		Edge를 PriorityQueue로 사용하려면 Edge가 distance를 가지거나 매번 이전역의 distance를 한번 더 호출해야하므로 SubwayNode를 priorityqueue에 사용함.
*/
class Edge{
  SubwayNode arrNode;
	long weight;

  public Edge(SubwayNode arrnode, long wei){
		this.arrNode = arrnode;
		this.weight = wei;

  }
	public SubwayNode getArrNode(){
    return this.arrNode;
  }
	public long getWeight(){
		return this.weight;
	}

}
