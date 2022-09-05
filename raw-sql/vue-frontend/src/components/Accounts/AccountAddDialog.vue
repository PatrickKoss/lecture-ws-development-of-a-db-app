<template>
  <v-layout id="root">
    <v-dialog :dark="state.dark" max-width="500" v-model="value">
      <v-card :dark="state.dark">
        <v-card-title class="headline" primary-title v-if="!edit">Add Account</v-card-title>
        <v-card-title class="headline" primary-title v-if="edit">Edit Account</v-card-title>

        <v-card-text>
          <v-row>
            <v-col cols="12">
              <v-text-field
                      :disabled="edit"
                      :error-messages="usernameErrors"
                      @blur="$v.username.$touch()"
                      @input="$v.username.$touch()"
                      hide-details="auto"
                      label="Username"
                      required
                      v-model="username"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-text-field
                      :error-messages="emailErrors"
                      @blur="$v.username.$touch()"
                      @input="$v.username.$touch()"
                      hide-details="auto"
                      label="Email"
                      required
                      v-model="email"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-text-field
                      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                      :error-messages="passwordErrors"
                      :type="showPassword ? 'text' : 'password'"
                      @blur="$v.password.$touch()"
                      @click:append="showPassword = !showPassword"
                      @input="$v.password.$touch()"
                      hide-details="auto"
                      label="Password"
                      required
                      v-model="password"
              />
            </v-col>
          </v-row>
        </v-card-text>

        <v-divider/>

        <v-card-actions>
          <v-btn @click="value = false" text>Cancel</v-btn>
          <v-spacer/>
          <v-btn @click="addAccount()" color="primary" text v-if="!edit">Add Account</v-btn>
          <v-btn @click="addAccount()" color="primary" text v-if="edit">Edit Account</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
  import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
  import {Validate} from "vuelidate-property-decorators";
  import {email, maxLength, minLength, required} from 'vuelidate/lib/validators'
  import {AccountsRestClient} from "../../model/accounts";

  @Component
  class AccountDeleteDialog extends Vue {
    @Prop() value;
    @Prop({default: {username: "", email: "", password: ""}}) account;
    @Prop({default: false}) edit;
    showPassword = false;
    emailErrors = [];
    usernameErrors = [];
    passwordErrors = [];
    state = this.$store.state;

    @Validate({required, maxLength: maxLength(100)})
    username = '';

    @Validate({required, email, maxLength: maxLength(300)})
    email = '';

    @Validate({required, minLength: minLength(6), maxLength: maxLength(50)})
    password = '';

    mounted() {
      window.addEventListener('keydown', this.keyDown, false);
    }

    /**
     * check if enter was clicked to submit
     * @param e
     */
    keyDown(e) {
      if (e.key === "Enter") this.addAccount();
    }

    async addAccount() {
      this.emailErrors = [];
      this.usernameErrors = [];
      this.passwordErrors = [];
      !this.$v.email.email && this.emailErrors.push('Must be valid e-mail');
      !this.$v.email.required && this.emailErrors.push('E-mail is required');
      !this.$v.email.maxLength && this.emailErrors.push('Email shouldn´t be longer than 300 signs');
      !this.$v.username.required && this.usernameErrors.push('Username is required');
      !this.$v.username.maxLength && this.usernameErrors.push('Username shouldn´t be longer than 50 signs');
      !this.$v.password.required && this.passwordErrors.push('Password is required');
      !this.$v.password.minLength && this.passwordErrors.push('Password should be at least 6 signs long');
      !this.$v.password.maxLength && this.passwordErrors.push('Password shouldn´t be longer than 50 signs');
      this.$v.$touch();

      if (this.emailErrors.length === 0 && this.usernameErrors.length === 0 && this.passwordErrors.length === 0) {
        if (!this.edit) {
          let user = {username: this.username, password: this.password, email: this.email};
          let response = await new AccountsRestClient().register(user);
          if ("status" in response && response.status != 200) {
            this.state.message = {messageType: "error", message: response.message};
          } else {
            this.value = false;
            this.$emit("reload-accounts");
            this.state.message = {messageType: "success", message: response.message};
          }
        } else {
          let user = {username: this.username, password: this.password, email: this.email};
          let response = await new AccountsRestClient().updateAccount(user);
          if ("status" in response && response.status != 200) {
            this.$emit("reload-accounts");
            this.state.message = {messageType: "error", message: response.message};
          } else {
            this.$emit("reload-accounts");
            this.state.message = {messageType: "success", message: response.message};
          }
        }
      }
    }

    @Watch("value")
    __dialog() {
      this.username = this.account.username;
      this.email = this.account.email;
      this.password = this.account.password;
      this.$emit("input", this.value);
    }

    @Watch("username")
    __resetUsernameErrors() {
      this.usernameErrors = [];
    }

    @Watch("email")
    __resetEmailErrors() {
      this.emailErrors = [];
    }

    @Watch("password")
    __resetPasswordErrors() {
      this.passwordErrors = [];
    }

    /**
     * clean up logic
     */
    beforeDestroy() {
      window.removeEventListener("keydown", this.keyDown, false);
    }
  }

  export default AccountDeleteDialog
</script>
<style scoped>
</style>
