
const app = Vue.
createApp({
    data() {
        return{
          
            clients: [],           
            accounts:[], 
            loans: [],
            amountTransfer:"",
            description:"",
            destinyAccount: [],
            originAccount:"",
            idAccount:""
        }

    },

    created(){

        axios.get('/api/clients/current')
        .then(response=> {
        console.log(response)
        this.clients = response.data;   
         
        this.accounts =response.data.accounts;
        console.log(this.accounts);
        
        this.loans = response.data.loans;
        console.log (this.loans);
        
    
        }
        
        )
        .catch(function (error) {
          
        })
      
    },
   
    methods: {


        newTransfer(){
            axios.post('/api/transactions', `amount=${this.amountTransfer}&description=${this.description}&originAccount=${this.originAccount}&destinyAccount=${this.destinyAccount}`,
             {headers:{'content-type':'application/x-www-form-urlencoded'}} )
            .then(response=>                 
                Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Transaction donel!',
                showConfirmButton: false,
                 timer:1500
            }))
            .then(response => window.location.href=`./account.html?id=${this.idAccount}` ) 
            .catch(error=> alert(error.response.data))
        },

        logOut(){
            axios.post('/api/logout').then(response =>  location.href = '/web/home.html')
        }
    },

    computed: {
       getIdAccount(){
        this.idAccount = this.accounts.filter(account => account.number == this.originAccount).map(account =>account.id);
    
        }

    },
    
}).mount('#app')



 