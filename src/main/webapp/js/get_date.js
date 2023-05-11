let get_date =
    json =>
    {
        let getTimePart = val => parseInt(val) < 10 ? '0' + val : val;

        let date = getTimePart(json.creationDate.date.day);
        let month = getTimePart(json.creationDate.date.month);
        let hour = getTimePart(json.creationDate.time.hour);
        let minute = getTimePart(json.creationDate.time.minute);
        let second = getTimePart(json.creationDate.time.second);

        return date + '-' + month + '-' + json.creationDate.date.year + ' '
            + hour + ':' + minute + ':' + second;
    }