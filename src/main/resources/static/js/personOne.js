/* global doT */

class Contact {

    constructor(value, element, parent) {
        this.parent = parent;
        let div = document.createElement('div');
        div.innerHTML = templates.contact({data: value});
        this.element = div.firstElementChild;
        element.appendChild(this.element);
        this.element.querySelector("button").addEventListener("click", (e) => this.delete());
        this.value = this.element.querySelector('[type="text"]');
        this.kind = this.element.querySelector('select');

        this.setValue(value);
        this.id = value.id;
    }
    setValue(value) {
        this.value.value = value.value;
        this.kind.value = value.kind;
    }
    getValue() {
        return {
            id: this.id,
            value: this.value.value,
            kind: this.kind.value
        };
    }
    delete() {
        this.element.remove();
        this.parent.removeChild(this);
    }
}
class ContactList {

    constructor(value, element) {
        this.children = [];
        this.element = element;
        element.parentElement.querySelector("button").addEventListener("click", (e) => {
            this.addChild({
                id: null,
                kind: 'email',
                value: ''
            });
        });
        value.forEach((v) => {
            this.addChild(v);
        });
    }
    addChild(v) {
        this.children.push(new Contact(v, this.element, this));
    }
    removeChild(child) {
        const index = this.children.indexOf(child);
        this.children.splice(index, 1);
    }
    getValue() {
        let value = [];
        this.children.forEach((e) => {
            value.push(e.getValue());
        });
        return value;
    }
}

class Address {
    constructor(value, element, constant) {
        let isNew = value === null;
        if (value === null) {
            value = this.getEmptyValue();
        }
        element.innerHTML = templates.address({data: value, constant: constant});
        this.checkbox = element.querySelector('[type="checkbox"]');
        this.hider = element.querySelector('.hider');
        [this.zipCode, this.settlement, this.address] = element.querySelectorAll('[type="text"]');
        this.checkbox.checked = !isNew;
        this.checkbox.addEventListener("input", (e) => {
            console.log(this.checkbox.checked);
            this.updateExist();
        });
        this.updateExist();
        let contactListE = element.querySelector('.contactList');
        this.contacts = new ContactList(value.contacts, contactListE);
        this.setValue(value);
    }
    setValue(value) {
        this.zipCode.value = value.zipCode;
        this.settlement.value = value.settlement;
        this.address.value = value.address;
    }
    getValue() {
        if (this.checkbox.checked) {
            return {
                zipCode: this.zipCode.value,
                settlement: this.settlement.value,
                address: this.address.value,
                contacts: this.contacts.getValue()
            };
        } else {
            return null;
        }
    }
    getEmptyValue() {
        return  {
            zipCode: '',
            settlement: '',
            address: '',
            contacts: []
        };
    }
    updateExist() {
        if (this.checkbox.checked) {
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
                    constantAddress: null,
                    temporaryAddress: null
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

            document.querySelector(".button.save").addEventListener("click", (e) => this.save());
            document.querySelectorAll(".button.del").forEach((e) => e.addEventListener("click", (e) => this.delete()));


            [this.lastName, this.firstName] = this.personE.querySelectorAll('[type="text"]');

            this.constantAddress = new Address(json.constantAddress, document.getElementById('constant'), true);
            this.temporaryAddress = new Address(json.temporaryAddress, document.getElementById('temporary'), false);

            this.setValue(json);

        } catch (error) {
            console.log(error);
            new Notify('Hiba a betöltés közben!', 'danger');


        }
    }
    setValue(value) {
        this.lastName.value = value.lastName;
        this.firstName.value = value.firstName;
    }
    getValue() {
        return {
            lastName: this.lastName.value,
            firstName: this.firstName.value,
            constantAddress: this.constantAddress.getValue(),
            temporaryAddress: this.temporaryAddress.getValue()
        };
    }
    async save() {
        let value = this.getValue();
        console.log(JSON.stringify(value));
        let method, url;
        if (this.new) {
            url = '/api/person'
            method = 'POST';
        } else {
            url = '/api/person/' + this.id;
            method = 'PUT';
        }
        let requestOptions = {
            method: method,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(value)
        };
        fetch(url, requestOptions).then(
                response => {
                    if (response.ok) {
                        new Notify('Sikeres mentés!', 'info');
                    } else {
                        new Notify('Hiba a mentés közben!', 'danger');
                    }
                }
        );
    }
    async delete() {

        fetch('/api/person/' + this.id, {method: 'DELETE'}).then(
                response => {
                    if (response.ok) {
                        new Notify('Sikeres törlés!', 'info');
                        window.location.href = "/person/list";
                    } else {
                        new Notify('Hiba a törlés közben!', 'danger');
                    }
                }
        );
    }

}

addEventListener("DOMContentLoaded", (event) => {
    new PersonOne();

});

