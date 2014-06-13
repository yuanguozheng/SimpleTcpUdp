using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace SocketExample
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void sTCP_Click(object sender, RoutedEventArgs e)
        {
            TCPExp tcp = new TCPExp();
            tcp.ShowDialog();
        }

        private void sUDP_Click(object sender, RoutedEventArgs e)
        {
            UDPExp udp = new UDPExp();
            udp.ShowDialog();
        }
    }
}
