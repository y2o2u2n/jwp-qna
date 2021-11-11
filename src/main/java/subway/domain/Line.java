package subway.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Line {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "line")
	// line 은 연관 관계 주인의 필드 이름. 누가 외래 키를 관리하는지 알려주는 역할. 만약 없다면 매핑 테이블이 추가된다. 외래 키를 담고 있으면 연관 관계의 주인.
	private List<Station> stations = new ArrayList<>();

	public void addStation(Station station) {
		stations.add(station);
		station.setLine(this);
	}

	protected Line() {

	}

	public Line(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Station> getStations() {
		return stations;
	}
}
