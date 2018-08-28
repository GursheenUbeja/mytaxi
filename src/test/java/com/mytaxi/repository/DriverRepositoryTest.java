
package com.mytaxi.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DriverRepositoryTest {

	@Autowired
	DriverRepository repository;

	@Test
	public void whenFindByUsername_returnDriver() {
		DriverDO driver = new DriverDO("abc", "abc");
		repository.save(driver);
		Assert.assertNotNull(repository.findByUsername("abc"));
	}

	@Test
	public void whenFindByOnlineStatus_returnDriverList() {

		List<DriverDO> findByOnlineStatus = repository.findByOnlineStatus(OnlineStatus.ONLINE);
		Assert.assertTrue((findByOnlineStatus.size() > 1));
	}

	@Test
	public void whenFindByDeleted_returnDriverList() {

		List<DriverDO> findByDeleted = repository.findByDeleted(false);
		Assert.assertTrue((findByDeleted.size() > 1));
	}

}