// admin storage page

window.addEventListener('load', function (){
    document.getElementById('delete-button').addEventListener('click', delete_fiber);
});

async function delete_fiber() {
    try {
        let data = new FormData(document.getElementById('fiber-id'));

        if (isNaN(parseInt(data.get('id').toString()))) {
            throw new IdError('id value isn\'t number');
        }

        let fiber_id = {
            'id': data.get('id')
        }

        let res = await handle_new_data(location.toString(), fiber_id, 'DELETE');
        fibers_composer(res);

    } catch (error) {
        if (error instanceof IdError) {
            alert(error.message);
        } else {
            throw error;
        }
    }
}

let fibers_composer =
    json =>
        {
            let element = document.getElementById('fibers_list');
            element.innerHTML = "";
            for (let item of json) {
                element.innerHTML +=
                    '<tr>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + get_date(item) + '</td>' +
                    '<td>' + item.section + '</td>' +
                    '<td>' + item.commentTo + '</td>' +
                    '</tr>';
            }
        }
