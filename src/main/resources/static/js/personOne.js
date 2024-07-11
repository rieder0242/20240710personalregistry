/* global doT */


class Address {
    constructor(value, element) {
        let isNew = value === null;
        if (value === null) {
            value = this.getEmptyValue();
        }
        element.innerHTML = templates.address({data: value});
        this.checkbox = element.querySelector('[type="checkbox"]');
        this.hider = element.querySelector('.hider');
        [this.zipCode, this.settlement, this.address] = element.querySelectorAll('[type="text"]');
        this.checkbox.checked = !isNew;
        this.checkbox.addEventListener("input", (e) => {
            console.log(this.checkbox.checked);
            this.updateExist();
        });
        this.updateExist();
        this.setValue(value);
    }
    setValue(value) {
        this.zipCode.value = value.zipCode;
        this.settlement.value = value.settlement;
        this.address.value = value.address;
    }
    getEmptyValue() {
        return  {
            zipCode: '',
            settlement: '',
            address: '',
            contacts: []
        };
    }
    updateExist(){
        if(this.checkbox.checked) {
            this.hider.style.display = '';            
        } else {
            this.hider.style.display = 'none';
        }
    }
}

class PersonOne {
    constructor() {
        this.id = document.getElementById('id').value;
        this.new = this.id == '';
        this.personE = document.getElementById("person");
        this.load();
    }
    async load() {
        try {
            let json;
            if (this.new) {
                json = {
                    firstName: '',
                    lastName: '',
                    addresses: []
                };
            } else {
                const url = "/api/person/" + this.id;
                const response = await fetch(url);
                if (!response.ok) {
                    throw new Error(`Response status: ${response.status}`);
                }

                json = await response.json();
            }
            console.log({data: json});
            this.personE.innerHTML = templates.person({data: json, new : this.new});

            this.constant = new Address(json.constantAddress, document.getElementById('constant'));
            this.constant = new Address(json.temporaryAddress, document.getElementById('temporary'));

        } catch (error) {
            console.log(error);
            document.getElementById("content").innerHTML = templates.error({message: "error"});
        }
    }

}

addEventListener("DOMContentLoaded", (event) => {
    new PersonOne();
});

