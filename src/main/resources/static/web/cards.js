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
            cards: [],
            today: [],
            cardToDelete:"",
           cardFilter: "",
            }

    },

    created(){

        axios.get('/api/clients/current')
        .then(response=> {
        this.clients = response.data;

        this.accounts = this.clients.accounts;
        //console.log(this.accounts)  

        this.transactions = this.accountsId.transactions;      
        //console.log(this.transactions);

        this.cards = this.clients.cards;
        //console.log(this.cards);
         this.cardFilter = (response.data.cards).filter(card => card.cardState == true)
            console.log(this.cardFilter);

        this.today = (new Date(Date.now())).toLocaleDateString();
       
    
    })
        .catch(function (error) {
          
        })
      
    },

   
    methods: {
        newDate(CreationDate){
            return new Date((CreationDate)).toLocaleDateString('es-AR', {month: 'numeric', year: '2-digit'});
        },

        expiredDate(value){
            return value = new Date(value).toLocaleDateString();
        },

        deleteCards(){
            
           axios.patch(`/api/clients/current/cards/cardState`,`number=${this.cardToDelete}`, 
           {headers:{'content-type':'application/x-www-form-urlencoded'}} )
           .then(response=>                 
               Swal.fire({
               position: 'top-end',
               icon: 'success',
               title: 'Disabled card!',
               showConfirmButton: false,
                timer:1500
           }))      
           .then(x=> window.location.reload() ) 
           .catch(error=> alert(error.response.data))
        },

        logOut(){
            axios.post('/api/logout').then(response => location.href = '/web/home.html')
        }
    },

    computed: {

    },
    
}).mount('#app')



 