let queryString = location.search
let id = new URLSearchParams(queryString).get("id")

const app = Vue.
    createApp({
        data() {
            return {

                clients: [],
                accounts: [],
                accountsId: [],
                transactions: [],
                cards: [],


                
                selectedColor: null,
                options: [
                  { text: 'SILVER', value: 'SILVER' },
                  { text: 'TITANIUM', value: 'TITANIUM' },
                  { text: 'GOLD', value: 'GOLD' }
                ],
                selectedType: null,
                options: [
                  { value: 'DEBIT',text: 'DEBIT',},
                  { text: 'CREDIT', value: 'CREDIT'},                 
                ]
            }

        },

        created() {

            axios.get('/api/clients/current')
                .then(response => {
                    this.clients = response.data;

                    this.neWcard = response.data.cards;
                  //  console.log(this.newCard)

                    this.transactions = this.accountsId.transactions;
                  //  console.log(this.transactions);

                    this.cards = this.clients.cards;
                   // console.log(this.cards);

                    this.cardFilter = (response.data.cards).filter(card => card.cardState == true)
            console.log(this.cardFilter);
                })
                .catch(function (error) {
                })
        },


        methods: {
            newDate(CreationDate) {
                return new Date(CreationDate).toLocaleDateString('es-AR', { month: 'numeric', year: '2-digit' });
            },

            logOut() {
                axios.post('/api/logout').then(response => location.href = '/web/home.html')
            },


            createCards() {
                axios.post('/api/clients/current/cards', `cardType=${this.selectedType}&cardColor=${this.selectedColor}`)
                .then(response => window.location.reload())
                .catch(response => alert("complete requested information"))
            },

            computed: {

            },
        }

     })
     .mount('#app');