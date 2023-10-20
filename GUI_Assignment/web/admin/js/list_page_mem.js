
function deleteMember(id) {
    if (confirm('Are you sure?'))
        window.location.href = "mem_list.jsp?id=" + id + "&delete=1";
}
