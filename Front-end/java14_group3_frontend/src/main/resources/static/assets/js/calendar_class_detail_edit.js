$(document).ready(function () {
    var listOfDayInput = document.getElementById("listOfDay");
    var eventDates = [];
    var listOfDayValue = listOfDayInput.value;
    eventDates = listOfDayValue;


    // const eventDates = ["2023-11-10", "2023-11-15", "2023-11-20"];
    const daysTag = document.querySelector(".form-edit-calendar .days-calendar"),
        currentDate = document.querySelector(".form-edit-calendar .current-date-calendar"),
        prevNextIcon = document.querySelectorAll(".form-edit-calendar .icons-calendar span");

// getting new date, current year and month
    let date = new Date(),
        currYear = date.getFullYear(),
        currMonth = date.getMonth();

// storing full name of all months in array
    const months = ["January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"];

    const renderCalendar = () => {
        let firstDayofMonth = new Date(currYear, currMonth, 1).getDay(), // getting first day of month
            lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate(), // getting last date of month
            lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay(), // getting last day of month
            lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate(); // getting last date of previous month
        let liTag = "";

        for (let i = firstDayofMonth; i > 0; i--) { // creating li of previous month last days
            liTag += `<li class="inactive">${lastDateofLastMonth - i + 1}</li>`;
        }

        for (let i = 1; i <= lastDateofMonth; i++) {
            // let isToday = i === date.getDate() && currMonth === new Date().getMonth()
            // && currYear === new Date().getFullYear() ? "active" : "";

            // Check if the current date is in the eventDates array
            let isEventDate = eventDates.includes(
                `${currYear}-${String(currMonth + 1).padStart(2, "0")}-${String(i).padStart(2, "0")}`
            );

            // Add an additional class if it's an event date
            liTag += `<li class="${isEventDate ? "active" : ""}">${i}</li>`;
        }

        for (let i = lastDayofMonth; i < 6; i++) { // creating li of next month first days
            liTag += `<li class="inactive">${i - lastDayofMonth + 1}</li>`
        }
        currentDate.innerText = `${months[currMonth]} ${currYear}`; // passing current mon and yr as currentDate text
        daysTag.innerHTML = liTag;
    }
    renderCalendar();

    prevNextIcon.forEach(icon => { // getting prev and next icons
        icon.addEventListener("click", () => { // adding click event on both icons
            // if clicked icon is previous icon then decrement current month by 1 else increment it by 1
            currMonth = icon.id === "prev" || icon.id === "prev1" ? currMonth - 1 : currMonth + 1;

            if (currMonth < 0 || currMonth > 11) { // if current month is less than 0 or greater than 11
                // creating a new date of current year & month and pass it as date value
                date = new Date(currYear, currMonth, new Date().getDate());
                currYear = date.getFullYear(); // updating current year with new date year
                currMonth = date.getMonth(); // updating current month with new date month
            } else {
                date = new Date(); // pass the current date as date value
            }
            renderCalendar(); // calling renderCalendar function
        });
    });

    var selectedDates = [];
    var listOfDayInput2 = document.getElementById("listOfDay2");
    var listOfDayValue22 = listOfDayInput2.value;
    listOfDayValue22 = listOfDayValue22.slice(1, -1);
    var dateStrings = listOfDayValue22.split(',').map(dateStr => dateStr.trim());
    var selectedDates = dateStrings.map(dateStr => new Date(dateStr));
    var listOfDayInput = document.getElementById("listClassDate");
    daysTag.addEventListener("click", function (event) {
        try {
            const clickedDate = new Date(Date.UTC(currYear, currMonth, event.target.innerText));
            if (isNaN(clickedDate.getDate())){
                return;
            }
            // Check if the clicked date is already in the selectedDates array
            const index = selectedDates.findIndex(date => date.getTime() === clickedDate.getTime());
            if (!event.target.classList.contains("active")) {
                // Date is not in the array, so add it
                selectedDates.push(clickedDate);
                event.target.classList.add("active");
            }
            else {
                // Date is already in the array, so remove it
                selectedDates.splice(index, 1);
                event.target.classList.remove("active");
            }

            // Update the value of the listClassDate input
            listOfDayInput.value = JSON.stringify(selectedDates);

        }
        catch (ex){

        }

    });
    listOfDayInput.value = JSON.stringify(selectedDates);

});