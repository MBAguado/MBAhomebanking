
const app = Vue.
    createApp({
        data() {
            return {

                pwd:"",
                email:"",
                firstNameRegister:"",
                lastNameRegister:"",
                emailRegister:"",
                pwdRegister:""

            }

        },
        created() {
          
        },
        methods: {
            signIn() {
                axios.post('/api/login',`email=${this.email}&pwd=${this.pwd}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => location.href = '/web/accounts.html')
                .catch(response => Swal.fire({
                    icon:'error',
                    title: 'Oops..!',
                    text: 'Your password or username is incorrect | Please try again.',
                }))
            },            
            logOut(){
                axios.post('/api/logout').then(response =>  location.href = '/web/home.html')
            },
            signUp(){
                axios.post('/api/clients',`firstName=${this.firstNameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.pwdRegister}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => axios.post('/api/login',`email=${this.emailRegister}&pwd=${this.pwdRegister}`))
                .then(response => location.href = '/web/accounts.html') 
            }
        }
    },     
)

.mount('#app');



