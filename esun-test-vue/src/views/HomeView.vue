<script setup lang="ts">
import MainLayout from '@/layouts/MainLayout.vue'
import StateHint from '@/components/general/StateHint.vue'
import FloorSeat from '@/components/HomeView/FloorSeat.vue'
import SubmitButton from '@/components/general/SubmitButton.vue'
import LoadingIndicator from '@/components/general/LoadingIndicator.vue'
import ErrorIndicator from '@/components/general/ErrorIndicator.vue'

import { ref, onMounted } from 'vue'
import type Seat from '@/types/Seat'
import type Employee from '@/types/Employee'
import PageState from '@/types/PageState'
import EmployeeService from '@/services/EmployeeService'

const initState = ref<PageState>(PageState.loading)
const updateState = ref<PageState>(PageState.loaded)
const seats = ref<Seat[] | undefined>()
const employees = ref<Employee[] | undefined>()

const selectedStaff = ref<Employee | undefined>()

const submit = () => {
  console.log('SUBMIT')
}

// Async Operation
const fetchData = async () => {
  if (initState.value != PageState.error) {
    initState.value = PageState.loading
  }
  await EmployeeService.fetchAllEmployee()
    .then((data) => {
      employees.value = data
      initState.value = PageState.loaded
    })
    .catch((error) => {
      console.log(error)
      initState.value = PageState.error
    })
}

onMounted(async () => {
  await fetchData()
})
</script>
<template>
  <MainLayout>
    <form
      v-if="initState === PageState.loaded"
      class="content-area"
      @submit.prevent="
        () => {
          submit()
        }
      "
    >
      <section class="state-hints">
        <StateHint title="空位" color="#d3d3d3" />
        <StateHint title="已佔用" color="#ff4d4d" />
        <StateHint title="已選擇" color="#7fffd4" />
      </section>
      <section class="seat-grid">
        <FloorSeat v-for="n in 16" :key="n" anchor="#staff-select"></FloorSeat>
      </section>
      <div class="form-field">
        <label for="staff-select">選擇欲佈位員工</label>
        <select id="staff-select" v-model="selectedStaff" required>
          <option :value="undefined" disabled>請選擇</option>
          <option v-for="employee in employees" :key="employee.id" :value="employee">
            {{ employee.name }}
          </option>
        </select>
      </div>
      <SubmitButton class="submit-btn" type="submit">送出</SubmitButton>
    </form>
    <div v-else class="indicators">
      <LoadingIndicator v-if="initState === PageState.loading" />
      <ErrorIndicator v-if="initState === PageState.error" />
    </div>
  </MainLayout>
</template>
<style scoped lang="scss">
.bg-normal {
  background-color: #d3d3d3;
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
  padding: 1rem 8vw;
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
</style>
