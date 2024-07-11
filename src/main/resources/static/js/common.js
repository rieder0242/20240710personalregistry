/* global doT */

const templates = {};
addEventListener("DOMContentLoaded", (event) => {
    document.querySelectorAll("script[type='text/x-dot-template']").forEach(
            (e) => {
        templates[e.id] = doT.compile(e.innerHTML);
    });
});


function pager(pos, max) {
    const result = {
        first: pos === 0,
        last: pos === max - 1,
        child: []
    };
    let last = 0;
    const section = (from, to) => {
        if (last < from ) {
            console.log(last < from + 1,last , from + 1);
            result.child.push({
                separator: true
            });
        }
        let i = Math.max(from, last),
                mx = Math.min(to, max-1);
        for (; i <= mx; i++) {
            result.child.push({
                separator: false,
                name: i + 1,
                data: i,
                current: i===pos
                ,pos:pos
            });
        }
        last = i;
    };
    const radius = 2;
    const radiusEnd = 1;
    section(0, radiusEnd);
    section(pos - radius, pos + radius);
    section(max - radiusEnd - 1 , max);
    return result;
}