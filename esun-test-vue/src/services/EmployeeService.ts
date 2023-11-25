import type Employee from '@/types/Employee'
import axios from 'axios'
import config from '@/services/config'
export default {
  async fetchAllEmployee(): Promise<Employee[]> {
    try {
      await axios({
        url: config.BASE_URL,
        method: 'get'
      })
      return []
    } catch (error) {
      throw new Error('Fetch All Employee Error！')
    }
  },
  async updateEmployeeSeat() {
    await axios({
      url: config.BASE_URL,
      method: 'post'
    })
  }
}
