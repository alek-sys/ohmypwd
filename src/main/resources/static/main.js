document.addEventListener('DOMContentLoaded', () => {
    if (window.fetch) {
        const bodyRef = new Hammer(document.body);
        bodyRef.on('swipe', () => {
            fetch('/api/password/single')
                .then(data => data.json())
                .then(password => {
                    const el = document.querySelector('h1');
                    el.innerHTML = `${password.first}<span>${password.nums}</span>${password.second}`;
                });
        });
    }
});