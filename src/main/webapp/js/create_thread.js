window.addEventListener('load', function (){
    document.getElementById('create-button').addEventListener('click', create_thread);
});

async function create_thread() {

    let formData = new FormData(document.getElementById('new_fiber'));

    let options = {
        method: 'POST',
        body: formData
    }

    clearInputs();
    closeModal(document.getElementById('new-fiber-modal'));

    await fetch(location.toString(), options);

    let fibers_container = document.getElementById('fibers');
    let date_and_id = fibers_container.firstElementChild.childNodes[0].wholeText;
    let id = date_and_id.match(/#\d+/)[0].substring(1, );

    let GET_options = {
        method: 'GET',
        headers: {
            'Type': 'ajax',
            'Last': id
        }
    }

    let res = await fetch(location.toString(), GET_options).then(response => response.json());
    fibers_composer(res, false);
}