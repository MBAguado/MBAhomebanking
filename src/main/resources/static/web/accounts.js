
const app = Vue.
createApp({
    data() {
        return{
            
            clients: [],           
            accounts:[], 
            loans: [],
           
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
        console.log(this.clients);
        }
        
        )
        .catch(function (error) {
          
        })
      
    },

    methods: {
        logOut(){
            axios.post('/api/logout').then(response =>  location.href = '/web/home.html')
        }  ,
        
     createAccount(){
            axios.post('/api/clients/current/accounts') 
            .then ( response=>Swal.fire({
                title: 'Are you sure?',
                text: "you will create a new account!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, create it!',
                timer: 2000
              }).then((result) => {
                if (result.isConfirmed) {
                  Swal.fire(
                    'Created!',
                    'You have a new account!',
                    'success'                   
                  ) 
                } 
              })
            )  
            
            .then(x =>window.location.reload())
            .catch(response => Swal.fire({
                icon:'error',
                title: 'Oops..!',
                text: 'You have 3 accounts. You cannot created more!',
            }));
        },
    },
    computed: {

    },
    
}).mount('#app')



 