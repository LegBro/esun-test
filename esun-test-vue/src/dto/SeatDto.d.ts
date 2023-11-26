import EmployeeDto from '@/dto/EmployeeDto'
export default interface SeatDto {
  floorSeatSeq: string
  floorNo: number
  seatNo: number
  employee: EmployeeDto | null
}
