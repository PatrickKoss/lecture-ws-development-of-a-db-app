<template>
  <v-layout id="root">
    <v-dialog :dark="state.dark" max-width="500" v-model="value">
      <v-card :dark="state.dark">
        <v-card-title class="headline" primary-title>Delete</v-card-title>

        <v-card-text>Do you really want to delete {{ account.username }}?</v-card-text>

        <v-divider/>

        <v-card-actions>
          <v-btn @click="value = false" text>Cancel</v-btn>
          <v-spacer/>
          <v-btn @click="deleteAccount()" color="primary" text>Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
  import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
  import {AccountsRestClient} from "../../model/accounts";

  @Component
  class AccountDeleteDialog extends Vue {
    @Prop() value;
    @Prop() account;
    state = this.$store.state;

    async deleteAccount() {
      let response = await new AccountsRestClient().deleteUser(this.account.username);
      if ("status" in response && response.status != 200) {
        this.$emit("reload-accounts");
        this.state.message = {messageType: "error", message: response.message};
      } else {
        this.$emit("reload-accounts");
        this.state.message = {messageType: "success", message: response.message};
      }
    }

    @Watch("value")
    __dialog() {
      this.$emit("input", this.value);
    }
  }

  export default AccountDeleteDialog
</script>
<style scoped>
</style>
