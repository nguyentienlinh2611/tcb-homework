package com.example.tcbhomework;

import com.example.tcbhomework.dto.AddingPoolStatusEnum;
import com.example.tcbhomework.exception.PoolNotFoundException;
import com.example.tcbhomework.service.PoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PoolServiceTest {
    @TestConfiguration
    static class PoolServiceTestContextConfiguration {
        @Bean
        public PoolService poolService() {
            return new PoolService();
        }
    }

    @Autowired
    private PoolService poolService;

    @Test
    public void givenNewPoolId_whenAddingToPool_thenReturnInsertedStatus() {
        int newPoolId = 1;
        int[] poolValues = {1, 2, 3, 4, 5};
        assertThat(poolService.addPool(newPoolId, poolValues)).isEqualTo(AddingPoolStatusEnum.INSERTED);
    }

    @Test
    public void givenExistingPoolId_whenAddingToPool_thenReturnAppendingStatus() {
        int newPoolId = 1;
        int[] poolValues = {1, 2, 3, 4, 5};
        assertThat(poolService.addPool(newPoolId, poolValues)).isEqualTo(AddingPoolStatusEnum.INSERTED);
    }

    @Test
    public void givenExistingPoolWithPercentile_whenQueryPool_thenReturnTotalCountResult() {
        int existingPoolId = 1;
        int[] poolValues = {1, 2, 3, 4, 5};
        int[] appendingPoolValues = {6, 7, 8, 9};
        assertThat(poolService.addPool(existingPoolId, poolValues)).isEqualTo(AddingPoolStatusEnum.INSERTED);
        assertThat(poolService.addPool(existingPoolId, appendingPoolValues)).isEqualTo(AddingPoolStatusEnum.APPENDED);
    }

    @Test
    public void givenNonexistentPoolId_whenQueryPool_thenThrowNotFoundException() {
        int nonexistentPoolId = 2;
        assertThatThrownBy(() -> poolService.queryPool(nonexistentPoolId, 100)).isInstanceOf(PoolNotFoundException.class);
    }
}
