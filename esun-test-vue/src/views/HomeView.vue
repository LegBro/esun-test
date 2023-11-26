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
// -initState:畫面初次載入的狀態
// -updateState:後續進行資料更動的狀態
// -hintMessage:表單輸入提醒
// -show***Modal:控制跳出對話框
const initState = ref<PageState>(PageState.loading)
const updateState = ref<PageState>(PageState.loaded)
const hintMessage = ref<string | undefined>()
const showSubmitCheckModal = ref<boolean>(false)
const showUnseatCheckModal = ref<boolean>(false)
// data
// -seats:所有座位
// -employees:所有員工
// -unseatEmployees(應變數):無座位員工
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
// -selectedSeat:已選座位
// -selectedEmployee:已選員工
const selectedSeat = ref<Seat | undefined>()
const selectedEmployee = ref<Employee | undefined>()
// function
const resetForm = () => {
  selectedSeat.value = undefined
  selectedEmployee.value = undefined
  updateState.value = PageState.loaded
}
const resetHint = () => {
  hintMessage.value = undefined
}
// handler
const onSeatSelected = (seat: Seat) => {
  // 當某座位被選擇
  // -先清除提示
  // -若選擇已佔用位置
  // ---跳出刪除提醒
  resetHint()
  selectedSeat.value = seat
  // 如果選的是已佔用的位置，跳出移除提醒
  if (seat.seatBy) {
    showUnseatCheckModal.value = true
  }
}
const onSubmit = () => {
  // 確認表單內容座位跟員工都選了
  // -若通過
  // ---跳出確認對話框
  // -若未通過
  // ---顯示相應提示
  if (selectedEmployee.value && selectedSeat.value) {
    if (selectedSeat.value.seatBy) {
      // 不合理情形
      hintMessage.value = '您透過非正當方式選擇了已佔用位置'
    } else {
      showSubmitCheckModal.value = true
    }
  } else if (!selectedSeat.value) {
    hintMessage.value = '您未選擇座位'
  } else {
    hintMessage.value = '您未選擇員工'
  }
}
const onSubmitCheckModalClose = () => {
  // 當不設置座位時
  // -收起對話框
  resetForm()
  showSubmitCheckModal.value = false
}
const onUnseatCheckModalClose = () => {
  // 當不解除座位時
  // -收起對話框
  resetForm()
  showUnseatCheckModal.value = false
}
// request operations
const initData = async () => {
  // 讀取所有員工和所有座位
  // -加上setTimeout是為了看清讀取狀態
  // -讀取了Seats之後，將其seatBy綁定至employee物件
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
        if (employees.value) {
          seats.value.forEach((seat) => {
            if (seat.seatBy) {
              seat.seatBy =
                employees.value?.find((employee) => employee.id === seat.seatBy!.id) ?? null
            }
          })
        }

        if (initState.value !== PageState.error) {
          initState.value = PageState.loaded
        }
      })
      .catch((error) => {
        console.log(error)
        initState.value = PageState.error
      })
  }, 1000)
}
const sendSetSeatRequest = async (employee: Employee, seat: Seat) => {
  // 替員工設置座位
  // - 如果成功
  // ---將座位Id設置給員工
  // ---將座位seatBy設置為員工
  // ---回復畫面
  // -如果失敗
  // ---顯示錯誤
  updateState.value = PageState.loading
  setTimeout(async () => {
    await EmployeeService.updateEmployeeSeat(employee, seat)
      .then((_) => {
        employee.seatId = seat.id
        seat.seatBy = employee
        updateState.value = PageState.loaded
        onSubmitCheckModalClose()
      })
      .catch((error) => {
        console.log(error)
        updateState.value = PageState.error
      })
  }, 1000)
}
const sendUnseatRequest = async (seat: Seat) => {
  // 替員工解除座位
  // - 如果成功
  // ---將員工座位設為null
  // ---將座位員工設為null
  // ---回復畫面
  // -如果失敗
  // ---顯示錯誤
  updateState.value = PageState.loading
  setTimeout(async () => {
    if (seat.seatBy) {
      await EmployeeService.updateEmployeeSeat(seat.seatBy, null)
        .then((_) => {
          updateState.value = PageState.loaded
          seat.seatBy!.seatId = null
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
          @click="onSeatSelected(seat)"
        ></FloorSeat>
      </section>
      <section class="form-field">
        <label for="staff-select">選擇欲佈位員工</label>
        <select id="staff-select" v-model="selectedEmployee" @change="resetHint()" required>
          <option :value="undefined" disabled>請選擇</option>
          <option
            v-for="employee in unseatEmployees"
            :key="employee.id + employee.name"
            :value="employee"
          >
            {{ employee.name }}
          </option>
        </select>
      </section>
      <p v-if="hintMessage" class="hint-message">{{ hintMessage }}</p>
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
.hint-message {
  display: inline-block;
  align-self: flex-end;
  padding: 0.5rem 1rem;
  background-color: rgb(250, 250, 150);
  font-weight: bold;
  color: rgb(145, 145, 0);
  border-radius: 8px;
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
