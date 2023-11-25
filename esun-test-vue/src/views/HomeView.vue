<script setup lang="ts">
import MainLayout from '@/layouts/MainLayout.vue'
import StateHint from '@/components/general/StateHint.vue'
import FloorSeat from '@/components/HomeView/FloorSeat.vue'
import SubmitButton from '@/components/general/SubmitButton.vue'
import LoadingIndicator from '@/components/general/LoadingIndicator.vue'
import ErrorIndicator from '@/components/general/ErrorIndicator.vue'
import DefaultModal from '@/components/general/modals/DefaultModal.vue'

import { ref, onMounted, computed } from 'vue'
import type Seat from '@/types/Seat'
import type Employee from '@/types/Employee'
import PageState from '@/types/PageState'
import EmployeeService from '@/services/EmployeeService'
import SeatService from '@/services/SeatService'

// ui
const initState = ref<PageState>(PageState.loading)
const updateState = ref<PageState>(PageState.loaded)
const showSubmitCheckModal = ref<boolean>(false)
const showUnseatCheckModal = ref<boolean>(false)
// data
const seats = ref<Seat[] | undefined>()
const employees = ref<Employee[] | undefined>()
const unseatEmployees = computed<Employee[]>(() => {
  if (employees.value) {
    return employees.value.filter((employee) => employee.seatId === null)
  } else {
    return []
  }
})
// form
const selectedSeat = ref<Seat | undefined>()
const selectedEmployee = ref<Employee | undefined>()
// function
const resetForm = () => {
  selectedSeat.value = undefined
  selectedEmployee.value = undefined
  updateState.value = PageState.loaded
}
// handler
const onEmployeeSelected = (seat: Seat) => {
  selectedSeat.value = seat
  // 如果選的是已佔用的位置，跳出移除提醒
  if (seat.seatBy) {
    showUnseatCheckModal.value = true
  }
}
const onSubmit = () => {
  // 確認表單內容已輸入
  if (selectedEmployee.value && selectedSeat.value) {
    // 如果選的座位不是空的就警示，反之跳出確認
    if (selectedSeat.value.seatBy) {
      // 警示
    } else {
      console.log('SUBMIT')
      showSubmitCheckModal.value = true
    }
  }
}
const onSubmitCheckModalClose = () => {
  resetForm()
  showSubmitCheckModal.value = false
}
const onUnseatCheckModalClose = () => {
  resetForm()
  showUnseatCheckModal.value = false
}
// request operations
const initData = async () => {
  if (initState.value != PageState.error) {
    initState.value = PageState.loading
  }
  // 設等待時間1000ms，以看清等待圖式
  setTimeout(async () => {
    await EmployeeService.fetchAllEmployee()
      .then((data) => {
        employees.value = data
      })
      .catch((error) => {
        console.log(error)
        initState.value = PageState.error
      })
    await SeatService.fetchAllSeats()
      .then((data) => {
        seats.value = data
        if (initState.value !== PageState.error) {
          initState.value = PageState.loaded
          console.log(data)
        }
      })
      .catch((error) => {
        console.log(error)
        initState.value = PageState.error
      })
  }, 1000)
}
const sendSetSeatRequest = async (employee: Employee, seat: Seat) => {
  // 做UPDATE後，若成功則返回，若失敗則讓使用者自己按黑色區塊跳出
  // 不論成功與否接清空form
  updateState.value = PageState.loading
  // 發update api
  setTimeout(async () => {
    await EmployeeService.updateEmployeeSeat(employee, seat)
      .then((_) => {
        updateState.value = PageState.loaded
        seat.seatBy = employee
        onSubmitCheckModalClose()
      })
      .catch((error) => {
        console.log(error)
        updateState.value = PageState.error
      })
  }, 1000)
}
const sendUnseatRequest = async (seat: Seat) => {
  updateState.value = PageState.loading
  // 發update api
  setTimeout(async () => {
    if (seat.seatBy) {
      await EmployeeService.updateEmployeeSeat(seat.seatBy, null)
        .then((_) => {
          updateState.value = PageState.loaded
          seat.seatBy = null
          onUnseatCheckModalClose()
        })
        .catch((error) => {
          console.log(error)
          updateState.value = PageState.error
        })
    }
  }, 1000)
}
// Lifecycle
onMounted(async () => {
  await initData()
})
</script>
<template>
  <MainLayout>
    <form
      v-if="initState === PageState.loaded"
      class="content-area"
      @submit.prevent="
        () => {
          onSubmit()
        }
      "
    >
      <section class="state-hints">
        <StateHint title="空位" color="#f1f1f1" />
        <StateHint title="已佔用" color="#ff4d4d" />
        <StateHint title="已選擇" color="#7fffd4" />
      </section>
      <section v-if="seats" class="seat-grid">
        <FloorSeat
          v-for="seat in seats"
          :key="seat.id"
          :seat="seat"
          :selected-seat-id="selectedSeat?.id"
          anchor="#staff-select"
          @click="onEmployeeSelected(seat)"
        ></FloorSeat>
      </section>
      <section class="form-field">
        <label for="staff-select">選擇欲佈位員工</label>
        <select id="staff-select" v-model="selectedEmployee" required>
          <option :value="undefined" disabled>請選擇</option>
          <option v-for="employee in unseatEmployees" :key="employee.id" :value="employee">
            {{ employee.name }}
          </option>
        </select>
      </section>
      <SubmitButton class="submit-btn" type="submit">送出</SubmitButton>
    </form>
    <div v-else class="indicators">
      <LoadingIndicator v-if="initState === PageState.loading" />
      <ErrorIndicator v-if="initState === PageState.error" />
    </div>
  </MainLayout>
  <!-- Modal -->
  <Teleport to="body">
    <!-- Update Modal -->
    <DefaultModal
      :key="showSubmitCheckModal.toString()"
      :show-modal="showSubmitCheckModal"
      @close="onSubmitCheckModalClose"
    >
      <div
        v-if="updateState === PageState.loaded && selectedEmployee && selectedSeat"
        class="modal"
      >
        <p>確定設置位置?</p>
        <p>
          員工：{{
            selectedEmployee?.name ? `${selectedEmployee.name}[${selectedEmployee.id}]` : '...'
          }}
        </p>
        <p>
          位置：{{
            selectedSeat ? `${selectedSeat.floorNumber}樓${selectedSeat.seatNumber}號座位` : '...'
          }}
        </p>
        <SubmitButton
          class="submit-btn"
          type="button"
          @click="sendSetSeatRequest(selectedEmployee, selectedSeat)"
          >送出</SubmitButton
        >
      </div>
      <div v-else class="indicators">
        <LoadingIndicator v-if="updateState === PageState.loading"></LoadingIndicator>
        <ErrorIndicator v-if="updateState === PageState.error"></ErrorIndicator>
      </div>
    </DefaultModal>
    <!-- DELETE Modal -->
    <DefaultModal
      :key="showUnseatCheckModal.toString()"
      :show-modal="showUnseatCheckModal"
      @close="onUnseatCheckModalClose"
    >
      <div v-if="updateState === PageState.loaded && selectedSeat" class="modal">
        <p>確定<span style="color: red">解除</span>員工座位?</p>
        <p>
          員工：{{
            selectedSeat?.seatBy ? `${selectedSeat.seatBy.name}[${selectedSeat.seatBy.id}]` : '...'
          }}
        </p>
        <p>
          騰出位置：{{
            selectedSeat ? `${selectedSeat.floorNumber}樓${selectedSeat.seatNumber}號座位` : '...'
          }}
        </p>
        <SubmitButton class="submit-btn" type="button" @click="sendUnseatRequest(selectedSeat)"
          >送出</SubmitButton
        >
      </div>
      <div v-else class="indicators">
        <LoadingIndicator v-if="updateState === PageState.loading"></LoadingIndicator>
        <ErrorIndicator v-if="updateState === PageState.error"></ErrorIndicator>
      </div>
    </DefaultModal>
  </Teleport>
</template>
<style scoped lang="scss">
.bg-normal {
  background-color: #f1f1f1;
}
.bg-disabled {
  background-color: #ff4d4d;
}
.bg-selected {
  background-color: #7fffd4;
}
//
.content-area {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 2rem;
  padding: 1rem 2rem;
}
.state-hints {
  display: flex;
  align-items: center;
  gap: 2.5rem;
}
.seat-grid {
  display: grid;
  gap: 2rem;

  @media only screen and (max-width: 767px) {
    grid-template-columns: repeat(1, 1fr);
  }
  @media only screen and (min-width: 768px) and (max-width: 991px) {
    /* 適用於768像素到991像素寬度的螢幕，通常是平板 */
    /* CSS樣式 */
    grid-template-columns: repeat(2, 1fr);
  }
  @media only screen and (min-width: 992px) and (max-width: 1199px) {
    /* 適用於992像素到1199像素寬度的螢幕，通常是較大的平板或桌面電腦 */
    /* CSS樣式 */
    grid-template-columns: repeat(3, 1fr);
  }
  @media only screen and (min-width: 1200px) {
    /* 適用於1200像素寬度以上的螢幕，通常是較大的桌面電腦 */
    /* CSS樣式 */
    grid-template-columns: repeat(4, 1fr);
  }
}
.submit-btn {
  align-self: flex-end;
}
.indicators {
  padding: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.modal {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
</style>
