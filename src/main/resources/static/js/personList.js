/* global doT */


class PersonList {
    constructor() {
        this.page = 0;
        this.filter = '';
        this.listE = document.getElementById("list");
        this.pagerE = document.getElementById("pager");
        let filterE = document.getElementById("filter");
        filterE.addEventListener("input", (e) => {
            console.log(filterE.value);
            this.filter = filterE.value;
            this.getPage();
        });
    }
    async getPage() {
        try {
            const url = "/api/person/list/" + this.page + '/' + this.filter;
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }

            const json = await response.json();
            this.listE.innerHTML = templates.main({page: json});
            this.pagerE.innerHTML = templates.pager({pager: pager(this.page, json.totalPages)});
            this.pagerE.querySelectorAll('a.pagination-link').forEach((e)=>{
                e.addEventListener("click", (ev) => {
                    this.page = e.dataset.page*1;
                    this.getPage();
                    ev.preventDefault();
                });
            });
            this.pagerE.querySelectorAll('a.pagination-previous').forEach((e)=>{
                e.addEventListener("click", (ev) => {
                    this.page--;
                    this.getPage();
                    ev.preventDefault();
                });
            });
            this.pagerE.querySelectorAll('a.pagination-next').forEach((e)=>{
                e.addEventListener("click", (ev) => {
                    this.page++;
                    this.getPage();
                    ev.preventDefault();
                });
            });
        } catch (error) {
            console.log(error);
            this.listE.innerHTML = templates.error({message: "error"});
            this.pagerE.innerHTML = '';
        }
    }

}

addEventListener("DOMContentLoaded", (event) => {
    new PersonList().getPage();
});

