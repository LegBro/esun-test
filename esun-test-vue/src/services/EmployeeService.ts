import type Employee from '@/types/Employee'
import axios from 'axios'
import config from '@/services/config'
import type EmployeeDto from '@/dto/EmployeeDto'
export default {
  async fetchAllEmployee(): Promise<Employee[]> {
    const responese = await axios({
      url: '/api/Employee/getAllEmployees',
      method: 'get'
    })
    const employeeDtos = responese.data as Array<EmployeeDto>
    return employeeDtos.map((dto) => {
      return {
        id: dto.id,
        name: dto.name,
        email: dto.email,
        seatId: dto.floorSeatSeq
      }
    })
  },
  async updateEmployeeSeat() {
    await axios({
      url: config.BASE_URL,
      method: 'post'
    })
  }
}
