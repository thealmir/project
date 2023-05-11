let fibers_composer =
    (json, isThread) =>
    {
        let element;

        if (isThread) {
            element = document.getElementById('fibers').lastElementChild;
        } else {
            element = document.getElementById('fibers');
        }

        for (let item of json) {
            let div = document.createElement('div');
            div.className = "item";

            let date = document.createTextNode(item['creation_date']);

            div.appendChild(date);

            let id = document.createTextNode(' #' + item.id);
            div.appendChild(id);

            div.appendChild(document.createElement('br'));

            for (let file of item['files']) {
                let type = file['name'].toString().substring(file['name'].lastIndexOf('.') + 1);
                let clear_name = file['name'].toString().substring(file['name'].indexOf(' ') + 1);
                switch (type) {
                    case 'jpg': case 'png':
                        let img = document.createElement('img');
                        img.className = 'image';
                        img.src = `/file?name=${file['name']}`;
                        img.loading = 'lazy';

                        div.appendChild(img);
                        div.appendChild(document.createElement('br'));
                        break;
                    case 'mp3':
                        let audio = document.createElement('div');
                        audio.className = 'audio';
                        audio.id = `${file['id']}`;
                        audio.appendChild(document.createTextNode(clear_name));

                        div.appendChild(audio);
                        div.appendChild(document.createElement('br'));
                        break;
                    case 'mp4':
                        let video = document.createElement('video');
                        video.controls = true;
                        video.loading = 'lazy';
                        let video_src = document.createElement('source');
                        video_src.type = 'video/mp4';
                        video_src.src = `/file?name=${file['name']}`;
                        video.appendChild(video_src);

                        div.appendChild(video);
                        div.appendChild(document.createElement('br'));
                        break;
                }
            }

            if (!isThread) {
                let anchor = document.createElement('a');
                anchor.href = `/fiber?fiber_id=${item.id}`;
                anchor.target = '_self';

                let br = document.createElement('br');
                let section = document.createTextNode(item.section);

                anchor.appendChild(br);
                anchor.appendChild(section);
                div.appendChild(anchor);

                element.insertBefore(div, element.firstChild);
            } else {
                let section = document.createTextNode(item.section);
                div.appendChild(section);

                element.appendChild(div);
            }
        }

    };