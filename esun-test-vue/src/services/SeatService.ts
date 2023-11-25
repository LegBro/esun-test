import axios from 'axios'
import type Seat from '@/types/Seat'
import type SeatDto from '@/dto/SeatDto'
export default {
  async fetchAllSeats(): Promise<Seat[]> {
    const responese = await axios({
      url: '/api/Seating/getAllSeatsWithEmployee',
      method: 'get'
    })
    const seatDtos = responese.data as Array<SeatDto>
    return seatDtos.map((dto) => {
      return {
        id: dto.floorSeatSeq,
        floorNumber: dto.floorNo,
        seatNumber: dto.seatNo,
        seatBy: dto.employee
          ? {
              id: dto.employee.id,
              name: dto.employee.name,
              email: dto.employee.email,
              seatId: dto.employee.floorSeatSeq
            }
          : null
      }
    })
  }
}
