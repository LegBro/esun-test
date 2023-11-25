import axios from 'axios'
import config from '@/services/config'
import type Seat from '@/types/Seat'
export default {
  async fetchAllSeat(): Promise<Seat[]> {
    await axios({
      url: config.BASE_URL,
      method: 'post'
    })
    return []
  }
}
