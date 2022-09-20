
const app = Vue.
    createApp({
        data() {
            return {
                clients: [],
                accounts: [],
                loans: [],
                loanId: 0,
                prestamo: "",
                paymentsValue: "",
                amountMax: [],
                amountClient: "",
                destinyAccount: [],
                
            }

        },

        created() {
            axios.get('/api/clients/current')
                .then(response => {
                    console.log(response)
                    this.clients = response.data;

                    this.accounts = response.data.accounts;
                    console.log(this.accounts);

                    this.loans = response.data.loans;
                    console.log(this.loans);
                })
                .catch(function (error) {
                })
        },

        methods: {
            dataLoan() {
                axios.get("/api/loans").then(response => {
                    this.loans = response.data;
                    this.typeLoan = this.data.name;
                    this.amountMax = this.data.amount;
                    this.paymentsValue = this.data.payments;
                    console.log(this.paymentsValue)
                })
                    .catch(function (error) {
                        console.log(error);
                    })
            },

            logOut() {
                axios.post('/api/logout').then(response => location.href = '/web/home.html')
            },

            changeLoan(valor) {
                this.loanId = valor

            },

            generateLoan(){
                axios.post('/api/loans', 
                    {
                        "id": this.loanId,
                        "amount": this.amountClient,
                        "payments": this.paymentsValue,
                        "destinyAccount": this.destinyAccount,
                    },
                )
                    .then(response =>
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Loan aproved!',
                            showConfirmButton: false,
                            timer: 1500
                        }))
                    .then(response => window.location.reload())
                    .catch(response => Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Please, complete all request.',
                        
                    }));
            },
           
        },

        computed: {

        },

    }).mount('#app')



 //Swal.fire("The transaction is not posible, " + "(" + error.response.data +")")