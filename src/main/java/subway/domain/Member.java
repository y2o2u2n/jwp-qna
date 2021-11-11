package subway.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany
	@JoinColumn(name = "member_id") // 가장 복잡. 연관 관계마다 동작 방식이 다름.
	private List<Favorite> favorites = new ArrayList<>();

	public void addFavorite(Favorite favorite) {
		favorites.add(favorite);
	}

	protected Member() {

	}

	public Member(String name) {
		this.name = name;
	}
}
