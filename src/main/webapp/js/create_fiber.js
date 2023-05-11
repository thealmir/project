window.addEventListener('load', function (){
    document.getElementById('create-button').addEventListener('click', create_comment);
});

async function create_comment() {
    try {
        let data = new FormData(document.getElementById('new_comment'));

        if (isNaN(parseInt(data.get('comment_to').toString()))) {
            throw new IdError('id value isn\'t number');
        }

        let options = {
            method: 'POST',
            body: data
        }

        clearInputs();
        closeModal(document.getElementById('new-fiber-modal'));

        await fetch(location.toString(), options);

        let fibers_container = document.getElementById('fibers');
        let date_and_id = fibers_container.lastElementChild.lastElementChild;
        // case only opening post exists
        if (date_and_id == null) {
            fibers_container = document.getElementsByClassName('opening-fiber').item(0);
            date_and_id = fibers_container.childNodes[0].wholeText;
        } else {
            date_and_id = date_and_id.childNodes[0].wholeText;
        }

        let id = date_and_id.match(/#\d+/)[0].substring(1, );

        let GET_options = {
            method: 'GET',
            headers: {
                'Type': 'ajax',
                'Last': id
            }
        }

        let res = await fetch(location.toString(), GET_options).then(response => response.json());
        fibers_composer(res, true);

    } catch (error) {
        if (error instanceof IdError) {
            alert(error.message);
        } else {
            throw error;
        }
    }
}