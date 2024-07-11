/* global doT */


class PersonOne {
    constructor() {
        this.id = document.getElementById('id').value;
        this.new = this.id=='';
        this.personE = document.getElementById("person");
        this.load();
    }
    async load(){
        try {
            const url = "/api/person/" + this.id ;
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }

            const json = await response.json();
            console.log({data: json});
            this.personE.innerHTML = templates.person({data: json, new:this.new});


        } catch (error) {
            console.log(error);
            document.getElementById("content").innerHTML = templates.error({message: "error"});
        }
    }


}

addEventListener("DOMContentLoaded", (event) => {
    new PersonOne();
});

