
const app = Vue.
createApp({
    data() {
        return{
            message: "hola desde vue",
            clients: [],
            firstName:"",
            lastName:"",
            email:"",
        }

    },

    created(){

        axios.get('/api/clients')
        .then(response=> {
    
         this.clients = response.data;
        console.log(this.clients);
        })
        .catch(function (error) {
          
        })
      
    },

   
    methods: {
 addClient(){
    if (this.firstName.length == "" || this.lastName.length == "" ||  this.email.length == "") { 
        return;    
    } else {
    console.log("llego")
                axios.post('/rest/clients', {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,
                })                                    
                  .then(response => {console.log(response)})
                    .catch(function (error) {
                        console.log(error);
                    }
                    ); 
            } }
        },
    computed: {

    },
}).mount('#app')



 