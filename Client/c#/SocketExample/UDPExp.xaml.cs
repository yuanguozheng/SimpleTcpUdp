using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace SocketExample
{
    /// <summary>
    /// UDPExp.xaml 的交互逻辑
    /// </summary>
    public partial class UDPExp : Window
    {
        public delegate void MessageHandler(string msg);

        static MessageHandler handler;

        UdpClient udp = new UdpClient();

        public UDPExp()
        {
            InitializeComponent();
            this.Loaded += UDPExp_Loaded;
        }

        public event MessageHandler ReceviedFromServer
        {
            add
            {
                handler += new MessageHandler(value);
            }
            remove
            {
                handler -= new MessageHandler(value);
            }
        }

        void UDPExp_Loaded(object sender, RoutedEventArgs e)
        {
            
        }

        async void DoReceive()
        {
            while (true)
            {
                UdpReceiveResult result = await udp.ReceiveAsync();
                string str = Encoding.UTF8.GetString(result.Buffer);
                handler(str);
            }
        }

        private void Send_Click(object sender, RoutedEventArgs e)
        {
            //UdpClient udp = new UdpClient();
            //udp.Connect(IPAddress.Parse(ServerIP.Text), int.Parse(Port.Text));
            byte[] data;
            data = Encoding.UTF8.GetBytes(SendContent.Text);
            udp.Send(data, data.Length);
        }

        private void Connect_Click(object sender, RoutedEventArgs e)
        {
            new Thread(DoReceive).Start();
            udp.Connect(IPAddress.Parse(ServerIP.Text), int.Parse(Port.Text));
            this.ReceviedFromServer += UDPExp_ReceviedFromServer;
        }

        void UDPExp_ReceviedFromServer(string msg)
        {
            Dispatcher.BeginInvoke((Action)(() =>
                {
                    Received.Text += msg + "\n";
                }));
        }

    }
}
