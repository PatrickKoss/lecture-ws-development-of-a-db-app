import axios from "axios";
import {config} from "../config";

export class AccountsRestClient {

  api = config.apiEndpoint;

  /**
   * get all accounts
   * @returns {Promise<{message: *, status: number}|T>}
   */
  async getAccounts() {
    try {
      return (await axios.get(`${this.api}/accounts`)).data;
    } catch (error) {
      return {message: error.response.data.message, status: error.response.status};
    }
  }

  /**
   * update an account
   * @param user
   * @returns {Promise<T>}
   */
  async updateAccount(user) {
    try {
      return (await axios.put(`${this.api}/accounts/${user.username}`, user)).data;
    } catch (error) {
      return {message: error.response.data.message, status: error.response.status};
    }
  }

  /**
   * delete an account
   * @returns {Promise<T>}
   */
  async deleteUser(id) {
    try {
      return (await axios.delete(`${this.api}/accounts/${id}`)).data;
    } catch (error) {
      return {message: error.response.data.message, status: error.response.status};
    }
  }

  /**
   * register a new account
   * @param user
   * @returns {Promise<T>}
   */
  async register(user) {
    try {
      return (await axios.post(`${this.api}/accounts`, user)).data;
    } catch (error) {
      return {message: error.response.data.message, status: error.response.status};
    }
  }
}
