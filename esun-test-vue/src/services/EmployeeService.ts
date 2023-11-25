import axios from 'axios'
import type Employee from '@/types/Employee'
import type Seat from '@/types/Seat'
import type EmployeeDto from '@/dto/EmployeeDto'
export default {
  async fetchAllEmployee(): Promise<Employee[]> {
    const responese = await axios.get('/api/Employee/getAllEmployees')
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

  async updateEmployeeSeat(employee: Employee, seat: Seat | null): Promise<void> {
    await axios.post('/api/Employee/setSeatOfEmployee', {
      employeeId: employee.id,
      seatId: seat?.id
    })
  }
}
