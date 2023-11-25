import type Employee from '@/types/Employee'
export default interface Seat {
  id: string
  floor: string
  seatNumber: number
  seatBy: Employee | null
}
