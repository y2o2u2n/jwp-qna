package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberTest {
	@Autowired
	private MemberRepository members;

	@Autowired
	private FavoriteRepository favorites;

	@Test
	void save() {
		Member expected = new Member("y2o2u2n");
		expected.addFavorite(favorites.save(new Favorite()));
		Member actual = members.save(expected);
		members.flush();
	}
}
