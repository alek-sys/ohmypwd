function updatePassword() {
    fetch('/api/password/single')
        .then(data => data.json())
        .then(password => {
            const el = document.querySelector('h1');
            el.innerHTML = `${password.first}<span>${password.nums}</span>${password.second}`;
        });
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelector("#refresh").addEventListener("click", () => {
        updatePassword();
    });

    new Clipboard('button');
});