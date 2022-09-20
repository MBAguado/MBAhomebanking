let queryString = location.search
let id = new URLSearchParams(queryString).get("id")

const app = Vue.
createApp({
    data() {
        return{
            
            clients: [],           
            accounts:[],          
            accountsId:[],  
            transactions:[],
            loans:[],
            resulted:[],
            amount:[],
            }
    },
    created(){
    
        axios.get('/api/clients/current')
        .then(response=> {
        this.clients = response.data;
        this.accounts = this.clients.accounts;
        console.log(this.accounts)
        this.accountsId = this.accounts.find(account => account.id == id) ;
        console.log(this.accountsId);
        this.transactions = this.accountsId.transactions;
        this.loans = response.data.loans;
        this.amount = this.loans.amount;
        
        console.log(this.amount),

        console.log(this.transactions),

        console.log(this.loans);
    })
        .catch(function (error) {
          
        })
      
    }, 
    methods: {
        newDate(CreationDate){
            return new Date(CreationDate).toLocaleDateString('es-AR', {day:'numeric', month: 'numeric', year:'numeric'});
        },
        logOut(){
            axios.post('/api/logout').then(response =>  location.href = '/web/home.html')
        },
        paymentWithInterest(){
            this.resulted=(this.input1-0)/(this.input2-0);
        }
    },


    computed: {

    },
    
}).mount('#app')



 