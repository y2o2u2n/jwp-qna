package subway.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // 이 안에 @Transactional이 있다.
public class StationTest {
	@Autowired
	private StationRepository stations; // 왜 stationRepository라 안쓰고 stations로 썼을까? 그냥 컬렉션이라고 생각해보라는 의미. 실무에서는 stationRepository라고 쓴다.

	@Test
	void save() {
		// given & when
		Station actual = stations.save(new Station("잠실역")); // Repository를 반환한 결과를 사용하는 것을 습관으로 들이는 것이 좋다.

		// then
		assertThat(actual.getId()).isNotNull();
	}

	@Test
	void findByName() {
		// given
		Station given = stations.save(new Station("잠실역"));

		// when
		Station actual = stations.findByName("잠실역");

		// then
		assertThat(actual).isEqualTo(given); // equals와 hashcode를 구현하지 않아도 같음을 보장한다. 왜? 영속성 컨텍스트에서 지원하는 기능. "동일성 보장"
		assertThat(actual).isSameAs(given);
		assertThat(actual.getId()).isEqualTo(given.getId());
		assertThat(actual.getName()).isEqualTo(given.getName());
	}

	@Test
	void findById() {
		// given
		Station given = stations.save(new Station("잠실역"));

		// when
		Station actual = stations.findById(given.getId()).get(); // 쿼리를 생성하지 않음. "1차 캐시"에 (ID,엔티티) 로 저장되기 때문

		// then
		assertThat(actual).isEqualTo(given);
		assertThat(actual).isSameAs(given);
		assertThat(actual.getId()).isEqualTo(given.getId());
		assertThat(actual.getName()).isEqualTo(given.getName());
	}

	@Test
	void changeName() {
		// given
		Station given = stations.save(new Station("잠실역"));

		// when
		given.changeName("몽촌토성역"); // "변경 감지"
		Station actual = stations.findByName("몽촌토성역");

		// then
		assertThat(actual).isNotNull();
	}

	@Test
	void changeName_fail() {
		// given
		Station given = stations.save(new Station("잠실역"));

		// when
		given.changeName("몽촌토성역");
		given.changeName("잠실역"); // 변경되는 것이 없음...
		Station actual = stations.findByName("몽촌토성역");

		// then
		assertThat(actual).isNull();
	}
}
