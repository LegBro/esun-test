import type Employee from '@/types/Employee'
export default interface Seat {
  id: string
  floorNumber: number
  seatNumber: number
  seatBy: Employee | null
}
