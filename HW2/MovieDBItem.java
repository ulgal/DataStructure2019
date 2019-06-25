
/******************************************************************************
 * MovieDB의 인터페이스에서 공통으로 사용하는 클래스.
 */
public class MovieDBItem implements Comparable<MovieDBItem> {

    private final String genre;
    private final String title;

    public MovieDBItem(String genre, String title) {
        if (genre == null) throw new NullPointerException("genre");
        if (title == null) throw new NullPointerException("title");

        this.genre = genre;
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    /**추가부분
     * -1또는 -2가 나올 경우
     * linkedList의 마지막이 아닌 경우에는 다음으로 넘어가고
     * 마지막인 경우 그 위치에 삽입한다.
     * 1 또는 2가 나올 경우 그 위치에 바로 전에 삽입하면 된다.
     * 같을 경우 넘어간다.
     */
    @Override
    public int compareTo(MovieDBItem other) {
        // TODO delete the line below and implement this method
        //throw new UnsupportedOperationException();
    	if(this.equals(other)) {
    		return 0;
    	}
    	else if(this.getGenre().equals(other.getGenre())){
        	if(this.getTitle().compareTo(other.getTitle())>0) {
        		return 1;
        	}
        	else if(this.getTitle().compareTo(other.getTitle())<0) {
        		return -1;
        	}
    	}
    	else if(this.getGenre().compareTo(other.getGenre())<0) {
    		return -2;
    	}
    	return 2;

    	//return 2; // 장르가 다를 경우 넘긴다.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MovieDBItem other = (MovieDBItem) obj;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

}
