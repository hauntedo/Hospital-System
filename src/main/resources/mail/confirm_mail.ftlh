<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Confirm</title>
    <style>
        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 1px solid rgba(0,0,0,.125);
            border-radius: 0.25rem;
        }
        .text-center {
            text-align: center!important;
        }
        *, ::after, ::before {
            box-sizing: border-box;
        }
        .card-header {
            padding: 0.5rem 1rem;
            margin-bottom: 0;
            background-color: rgba(0,0,0,.03);
            border-bottom: 1px solid rgba(0,0,0,.125);
        }
        .card-header:first-child {
            border-radius: calc(0.25rem - 1px) calc(0.25rem - 1px) 0 0;
        }
        .btn {
            display: inline-block;
            font-weight: 400;
            line-height: 1.5;
            color: #212529;
            text-align: center;
            text-decoration: none;
            vertical-align: middle;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
            background-color: transparent;
            border: 1px solid transparent;
            padding: 0.375rem 0.75rem;
            font-size: 1rem;
            border-radius: 0.25rem;
            transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
        }
        .btn-primary {
            color: #fff;
            background-color: #0d6efd;
            border-color: #0d6efd;
        }
        .card-body {
            margin-left: auto;
            margin-right: auto;
            flex: 1 1 auto;
            padding: 1rem 1rem;
        }
        .text-muted {
            --bs-text-opacity: 1;
            color: #6c757d!important;
        }
        .card-footer:last-child {
            border-radius: 0 0 calc(0.25rem - 1px) calc(0.25rem - 1px);
        }
        .card-footer {
            padding: 0.5rem 1rem;
            background-color: rgba(0,0,0,.03);
            border-top: 1px solid rgba(0,0,0,.125);
        }
    </style>
</head>
<body>
<div class="card text-center">
    <div class="card-body">
        <p><strong>Hi, ${first_name}!</strong></p>
        <br>
        <p>Don't worry, you can your account from this link: <strong>
                <a href="${action_link}" target="_blank">Confirm</a>
            </strong></p>
        <p>The code will be valid once.</p>
        <p>If you did not request a confirm, simply delete this email.</p>
        <p>Have a good mood!</p>
    </div>
</div>

</body>
</html>